package agency.tango.viking.bindings.map.mapTypes;

import agency.tango.viking.bindings.map.BindableItem;
import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;
import agency.tango.viking.bindings.map.adapters.ItemPopupAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickHandler;
import agency.tango.viking.bindings.map.listeners.ICameraIdleListener;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableList;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public class GoogleMapView<T> extends MapView
{
    public static final float DEFAULT_ZOOM = 15;
    private ArrayList<ICameraIdleListener> cameraIdleListeners = new ArrayList<>();

    private ItemClickHandler itemClickHandler;
    private ItemPopupAdapter infoWindowAdapter;
    private Polyline path;
    private List<Marker> markers = new ArrayList<>();

    private BindableItem<Location> location = new BindableItem<>();
    private BindableItem<Float> zoom = new BindableItem<>();
    private BindableItem<Integer> radius = new BindableItem<>();

    private IMapItemAdapter mapAdapter;
    private ObservableList.OnListChangedCallback<ObservableList<T>> itemsListener;

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
            initGoogleMap();

            registerOnCameraIdleListener(() -> {
                updateField(location, getLocation(googleMap));
                updateField(zoom, googleMap.getCameraPosition().zoom);
                updateField(radius, currentRadius(googleMap));
            });

            googleMap.setOnCameraIdleListener(() -> {
                for (ICameraIdleListener cameraIdleListener : cameraIdleListeners)
                {
                    cameraIdleListener.onCameraIdle();
                }
            });

            googleMap.setOnMarkerClickListener(markerClicked -> {
                for (Marker marker : markers)
                {
                    if (marker.equals(markerClicked))
                    {
                        onMarkerClick((T) marker.getTag());
                        return false;
                    }
                }
                return true;
            });
        });

        itemsListener = new ObservableList.OnListChangedCallback<ObservableList<T>>()
        {
            @Override
            public void onChanged(ObservableList<T> observableList)
            {
                addItems(observableList, true);
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> observableList, int fromIndex, int itemCount)
            {
                for (int i = fromIndex; i < itemCount; i++)
                {
                    Marker marker = markers.get(i);

                    T model = observableList.get(i);
                    marker.setTag(model);

                    marker.setIcon(BitmapDescriptorFactory.fromResource(mapAdapter.icon(model)));
                    marker.setPosition(mapAdapter.position(model));
                }
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> observableList, int fromIndex, int itemCount)
            {
                getMapAsync(googleMap -> {
                    for (int i = fromIndex; i < itemCount; i++)
                    {
                        T item = observableList.get(i);
                        Marker marker = createMarker(googleMap, item);

                        markers.add(i, marker);
                    }
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> observableList, int fromPosition, int toPosition, int itemCount)
            {
                moveRange(markers, fromPosition, toPosition, itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> observableList, int fromIndex, int itemCount)
            {
                getMapAsync(googleMap1 -> {
                    for (int i = fromIndex; i < itemCount; i++)
                    {
                        Marker marker = markers.remove(i);
                        if (marker != null)
                        {
                            marker.remove();
                        }
                    }
                });
            }
        };
    }

    @NonNull
    private Marker createMarker(GoogleMap googleMap, T item)
    {
        Marker marker = googleMap.addMarker(getMarkerOptions(item));
        marker.setTag(item);
        return marker;
    }

    public void registerOnCameraIdleListener(ICameraIdleListener cameraIdleListener)
    {
        cameraIdleListeners.add(cameraIdleListener);
    }

    private void initGoogleMap()
    {
        getMapAsync(googleMap -> {
            if (checkLocationPermission())
            {
                googleMap.setMyLocationEnabled(true);
            }
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        });
    }

    private boolean checkLocationPermission()
    {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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

    private Location getLocation(GoogleMap googleMap)
    {
        Location location = new Location("user");
        location.setLatitude(googleMap.getCameraPosition().target.latitude);
        location.setLongitude(googleMap.getCameraPosition().target.longitude);
        return location;
    }

    private int currentRadius(GoogleMap googleMap)
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

    public void popupInfoAdapter(ItemPopupAdapter infoWindowAdapter)
    {
        this.infoWindowAdapter = infoWindowAdapter;
        getMapAsync(googleMap -> googleMap.setInfoWindowAdapter(infoWindowAdapter));
    }

    public void markerPopupClicked(ItemClickHandler clickHandler)
    {
        getMapAsync(googleMap -> googleMap.setOnInfoWindowClickListener(markerClicked -> {
            for (Marker marker : markers)
            {
                if (marker.equals(markerClicked))
                {
                    clickHandler.onClick(marker.getTag());
                }
            }
        }));
    }

    private void addMarkersToMap(Collection<T> items)
    {
        getMapAsync(googleMap -> {
            for (T item : items)
            {
                Marker marker = createMarker(googleMap, item);
                markers.add(marker);
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

    public void addItems(Collection<T> items)
    {
        addItems(items, false);
    }

    public void addItems(Collection<T> items, boolean clear)
    {
        if (clear)
        {
            markers.clear();
        }

        addMarkersToMap(items);
    }

    public void insertItems(Collection<T> items)
    {
        if (items instanceof ObservableList)
        {
            ((ObservableList<T>) items).addOnListChangedCallback(itemsListener);
        }
        addItems(items);
    }
}