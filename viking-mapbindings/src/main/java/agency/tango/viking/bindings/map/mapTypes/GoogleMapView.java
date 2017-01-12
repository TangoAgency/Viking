package agency.tango.viking.bindings.map.mapTypes;

import agency.tango.viking.bindings.map.BindableItem;
import agency.tango.viking.bindings.map.ClusterIconRenderer;
import agency.tango.viking.bindings.map.IClusterMapItem;
import agency.tango.viking.bindings.map.adapters.IClusterItemAdapter;
import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;
import agency.tango.viking.bindings.map.adapters.ItemPopupAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ClusterClickHandler;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickHandler;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableArrayList;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import agency.tango.viking.bindings.map.listeners.ICameraIdleListener;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GoogleMapView<T> extends MapView
{
    public static final float DEFAULT_ZOOM = 15;
    private ArrayList<ICameraIdleListener> cameraIdleListeners = new ArrayList<>();
    protected GoogleMap googleMap;

    private ItemClickHandler itemClickHandler;
    private ItemPopupAdapter infoWindowAdapter;
    private Polyline path;
    private Map<T, Marker> markerList = new HashMap<>();

    private HeatmapTileProvider heatmapTileProvider;
    private Gradient gradient = HeatmapTileProvider.DEFAULT_GRADIENT;
    private double opacity = HeatmapTileProvider.DEFAULT_OPACITY;
    private int heatRadius = HeatmapTileProvider.DEFAULT_RADIUS;

//    private ClusterManager clusterManager;

    private BindableItem<Location> location = new BindableItem<>();
    private BindableItem<Float> zoom = new BindableItem<>();
    private BindableItem<Integer> radius = new BindableItem<>();

    private IClusterItemAdapter clusterAdapter;
    private IMapItemAdapter mapAdapter;

    public GoogleMapView(Context context)
    {
        super(context);
        init();
    }

    public GoogleMapView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        init();
    }

    public GoogleMapView(Context context, AttributeSet attributeSet, int i)
    {
        super(context, attributeSet, i);
        init();
    }

    public void init()
    {
        getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            initGoogleMap();

            registerOnCameraIdleListener(() -> {
                updateField(location, retrieveNewLocation());
                updateField(zoom, googleMap.getCameraPosition().zoom);
                updateField(radius, currentRadius());
            });


            googleMap.setOnCameraIdleListener(() -> {
                for (ICameraIdleListener cameraIdleListener : cameraIdleListeners)
                {
                    cameraIdleListener.onCameraIdle();
                }
            });

            if (clusterAdapter != null)
            {
//                initializeClusterManagerSettings();
            }
            else
            {

                googleMap.setOnMarkerClickListener(marker -> {
                    for (Map.Entry<T, Marker> entry : markerList.entrySet())
                    {
                        if (entry.getValue().equals(marker))
                        {
                            onMarkerClick(entry.getKey());
                            return false;
                        }
                    }
                    return true;
                });
            }
        });
    }

    public void registerOnCameraIdleListener(ICameraIdleListener cameraIdleListener)
    {
        cameraIdleListeners.add(cameraIdleListener);
    }

    private void initGoogleMap()
    {
        if (checkLocationPermission())
        {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private boolean checkLocationPermission()
    {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    public void clusterAdapter(IClusterItemAdapter clusterAdapter)
    {
        this.clusterAdapter = clusterAdapter;
    }

    public void mapAdapter(IMapItemAdapter mapAdapter)
    {
        this.mapAdapter = mapAdapter;
    }

    public BindableItem<Integer> radius()
    {
        return radius;
    }

    public BindableItem<Location> location()
    {
        return location;
    }

    public BindableItem<Float> zoom()
    {
        return zoom;
    }

    public void postChangedLocation(Location location)
    {
        getMapAsync(googleMap1 -> {
            googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
        });
        updateField(this.location, location);
    }

    protected <E> void updateField(BindableItem<E> item, E value)
    {
        item.setValue(value);
        item.onValueChanged(value);
    }

    private Location retrieveNewLocation()
    {
        Location location = new Location("user");
        location.setLatitude(googleMap.getCameraPosition().target.latitude);
        location.setLongitude(googleMap.getCameraPosition().target.longitude);
        return location;
    }

    private int currentRadius()
    {
        LatLngBounds latLngBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng widthLeftSide = new LatLng(latLngBounds.southwest.latitude, 0f);
        LatLng widthRightSide = new LatLng(latLngBounds.northeast.latitude, 0f);

        LatLng heightTopSide = new LatLng(0f, latLngBounds.northeast.longitude);
        LatLng heightBottomSide = new LatLng(0f, latLngBounds.southwest.longitude);

        int width = (int) SphericalUtil.computeDistanceBetween(widthRightSide, widthLeftSide);
        int height = (int) SphericalUtil.computeDistanceBetween(heightTopSide, heightBottomSide);

        return width > height ? width : height;
    }

    public void markerClicked(ItemClickHandler itemClickHandler)
    {
        this.itemClickHandler = itemClickHandler;
    }

    public void onMarkerClick(T item)
    {
        if (infoWindowAdapter != null)
        {
            infoWindowAdapter.itemClicked(item);
        }
        else
        {
            itemClickHandler.onClick(item);
        }
    }

    public void path(PolylineOptions polyline)
    {
        getMapAsync(googleMap -> {
            if (path != null)
            {
                path.remove();
            }
            path = googleMap.addPolyline(polyline);
        });
    }

    public void groundOverlay(@DrawableRes int groundOverlayImage, LatLngBounds latLngBounds)
    {
        GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(groundOverlayImage))
                .positionFromBounds(latLngBounds);

        getMapAsync(googleMap -> googleMap.addGroundOverlay(overlayOptions));
    }

    public void addItems(Collection<T> items)
    {
        addMarkersToMap(items);

        if (items.size() > 0)
        {
            ArrayList<LatLng> locations = new ArrayList<>();
            for (T item : items)
            {
                locations.add(mapAdapter.position(item));
            }

            heatmapTileProvider = new HeatmapTileProvider.Builder()
                    .gradient(gradient)
                    .opacity(opacity)
                    .radius(heatRadius)
                    .data(locations)
                    .build();

            getMapAsync(googleMap -> {
                googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
            });
//
//            if (clusterManager != null)
//            {
//                clusterManager.addItems(items);
//                clusterManager.cluster();
//            }
        }
    }

    public void popupInfoAdapter(ItemPopupAdapter infoWindowAdapter)
    {
        this.infoWindowAdapter = infoWindowAdapter;
        getMapAsync(googleMap -> {
            googleMap.setInfoWindowAdapter(infoWindowAdapter);
//
//            if (infoWindowAdapter != null)
//            {
//                googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
//                googleMap.setOnInfoWindowClickListener(clusterManager);
//                clusterManager.getMarkerCollection().setOnInfoWindowAdapter(infoWindowAdapter);
//            }
        });
    }

    public void markerPopupClicked(ItemClickHandler clickHandler)
    {
        getMapAsync(googleMap -> googleMap.setOnInfoWindowClickListener(marker -> {
            for (Map.Entry<T, Marker> entry : markerList.entrySet())
            {
                if (entry.getValue().equals(marker))
                {
                    clickHandler.onClick(entry.getKey());
                }
            }

//            clusterManager.setOnClusterItemInfoWindowClickListener(clickHandler::onClick);
        }));
    }

    private void addMarkersToMap(Collection<T> items)
    {
        getMapAsync(googleMap -> {
            for (T item : items)
            {
                Marker marker = googleMap.addMarker(getMarkerOptions(item));
                markerList.put(item, marker);
            }
        });
    }

    private MarkerOptions getMarkerOptions(T item)
    {
        MarkerOptions options = new MarkerOptions();
        if (mapAdapter.icon(item) != 0)
        {
            options.icon(BitmapDescriptorFactory.fromResource(mapAdapter.icon(item)));
        }
        options.position(mapAdapter.position(item));
        return options;
    }

    public void heatMapOpacity(double opacity)
    {
        this.opacity = opacity;
    }

    public void heatMapGradient(Gradient gradient)
    {
        this.gradient = gradient;
    }

    public void heatMapRadius(int heatRadius)
    {
        this.heatRadius = heatRadius;
    }

//    public void clusterClicked(ClusterClickHandler clusterClickHandler)
//    {
//        getMapAsync(googleMap ->
//                clusterManager.setOnClusterClickListener(cluster -> {
//                    clusterClickHandler.clusterClick(cluster);
//                    return false;
//                }));
//    }

//
//    private void initializeClusterManagerSettings()
//    {
//        clusterManager = new ClusterManager<>(getContext(), googleMap);
//
//        googleMap.setOnMarkerClickListener(clusterManager);
//
//        registerOnCameraIdleListener(() -> clusterManager.onCameraIdle());
//
//        clusterManager.setOnClusterItemClickListener(clusterItem -> {
//            onMarkerClick(clusterItem);
//            return false;
//        });
//
//
//        clusterManager.setRenderer(new ClusterIconRenderer(getContext(), googleMap, clusterManager, markerIcon()));
//    }
}