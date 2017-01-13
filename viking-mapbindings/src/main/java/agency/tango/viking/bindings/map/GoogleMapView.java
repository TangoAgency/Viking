package agency.tango.viking.bindings.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collection;

import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;
import agency.tango.viking.bindings.map.adapters.ItemPopupAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickHandler;
import agency.tango.viking.bindings.map.listeners.ICameraIdleListener;

public class GoogleMapView<T> extends MapView {
  public static final float DEFAULT_ZOOM = 15;
  private ArrayList<ICameraIdleListener> cameraIdleListeners = new ArrayList<>();

  private ItemClickHandler itemClickHandler;
  private ItemPopupAdapter infoWindowAdapter;

  private BindableItem<Location> location = new BindableItem<>();
  private BindableItem<Float> zoom = new BindableItem<>();
  private BindableItem<Integer> radius = new BindableItem<>();

  private MarkerManager<T> markerManager;
  private PathManager pathManager;

  private IMapItemAdapter mapAdapter;

  public GoogleMapView(Context context) {
    super(context);
    init();
  }

  public GoogleMapView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    init();
  }

  public GoogleMapView(Context context, AttributeSet attributeSet, int i) {
    super(context, attributeSet, i);
    init();
  }

  public void init() {
    markerManager = new MarkerManager<>(this::getMapAsync);
    pathManager = new PathManager(this::getMapAsync);

    getMapAsync(googleMap -> {
      initGoogleMap();

      registerOnCameraIdleListener(() -> {
        updateField(location, getLocation(googleMap));
        updateField(zoom, googleMap.getCameraPosition().zoom);
        updateField(radius, currentRadius(googleMap));
      });

      googleMap.setOnCameraIdleListener(() -> {
        for (ICameraIdleListener cameraIdleListener : cameraIdleListeners) {
          cameraIdleListener.onCameraIdle();
        }
      });
      //
      //            googleMap.setOnMarkerClickListener(markerClicked -> {
      //                for (Marker marker : markers)
      //                {
      //                    if (marker.equals(markerClicked))
      //                    {
      //                        onMarkerClick((T) marker.getTag());
      //                        return false;
      //                    }
      //                }
      //                return true;
      //            });
    });
  }

  public void registerOnCameraIdleListener(ICameraIdleListener cameraIdleListener) {
    cameraIdleListeners.add(cameraIdleListener);
  }

  private void initGoogleMap() {
    getMapAsync(googleMap -> {
      if (checkLocationPermission()) {
        googleMap.setMyLocationEnabled(true);
      }
      googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    });
  }

  private boolean checkLocationPermission() {
    return
        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  public void mapAdapter(IMapItemAdapter<T> mapAdapter) {
    this.mapAdapter = mapAdapter;
    markerManager.setItemsAdapter(mapAdapter);
  }

  public BindableItem<Integer> radius() {
    return radius;
  }

  public BindableItem<Location> location() {
    return location;
  }

  public BindableItem<Float> zoom() {
    return zoom;
  }

  public void postChangedLocation(Location location) {
    getMapAsync(googleMap -> {
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
          new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
    });
    updateField(this.location, location);
  }

  protected <E> void updateField(BindableItem<E> item, E value) {
    item.setValue(value);
    item.onValueChanged(value);
  }

  private Location getLocation(GoogleMap googleMap) {
    Location location = new Location("user");
    location.setLatitude(googleMap.getCameraPosition().target.latitude);
    location.setLongitude(googleMap.getCameraPosition().target.longitude);
    return location;
  }

  private int currentRadius(GoogleMap googleMap) {
    LatLngBounds latLngBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
    LatLng widthLeftSide = new LatLng(latLngBounds.southwest.latitude, 0f);
    LatLng widthRightSide = new LatLng(latLngBounds.northeast.latitude, 0f);

    LatLng heightTopSide = new LatLng(0f, latLngBounds.northeast.longitude);
    LatLng heightBottomSide = new LatLng(0f, latLngBounds.southwest.longitude);

    int width = (int) SphericalUtil.computeDistanceBetween(widthRightSide, widthLeftSide);
    int height = (int) SphericalUtil.computeDistanceBetween(heightTopSide, heightBottomSide);

    return width > height ? width : height;
  }

  public void markerClicked(ItemClickHandler itemClickHandler) {
    this.itemClickHandler = itemClickHandler;
  }

  public void onMarkerClick(T item) {
    if (infoWindowAdapter != null) {
      infoWindowAdapter.itemClicked(item);
    } else {
      itemClickHandler.onClick(item);
    }
  }
  //
  //public void path(PolylineOptions polyline) {
  //  getMapAsync(googleMap -> {
  //    if (path != null) {
  //      path.remove();
  //    }
  //    path = googleMap.addPolyline(polyline);
  //  });
  //}

  public void groundOverlay(@DrawableRes int groundOverlayImage, LatLngBounds latLngBounds) {
    GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
        .image(BitmapDescriptorFactory.fromResource(groundOverlayImage))
        .positionFromBounds(latLngBounds);

    getMapAsync(googleMap -> googleMap.addGroundOverlay(overlayOptions));
  }

  public void popupInfoAdapter(ItemPopupAdapter infoWindowAdapter) {
    this.infoWindowAdapter = infoWindowAdapter;
    getMapAsync(googleMap -> googleMap.setInfoWindowAdapter(infoWindowAdapter));
  }

  //    public void markerPopupClicked(ItemClickHandler clickHandler)
  //    {
  //        getMapAsync(googleMap -> googleMap.setOnInfoWindowClickListener(markerClicked -> {
  //            for (Marker marker : markers)
  //            {
  //                if (marker.equals(markerClicked))
  //                {
  //                    clickHandler.onClick(marker.getTag());
  //                }
  //            }
  //        }));
  //    }

  public void markers(Collection<T> items) {
    getMapAsync(googleMap -> markerManager.addItems(googleMap, items));
  }

  public void paths(Collection<PolylineOptions> paths) {
    getMapAsync(googleMap -> pathManager.addItems(googleMap, paths));
  }

  //    public void overlays(Collection<T> items)
  //    {
  //        getMapAsync(googleMap -> {
  //            PathManager<T> pathsManager = new PathsManager<T>();
  //            pathsManager.addItems(googleMap, items);
  //        });
  //    }
  //
  //    public void heatmap(Collection<T> items)
  //    {
  //        getMapAsync(googleMap -> {
  //            PathManager<T> pathsManager = new PathsManager<T>();
  //            pathsManager.addItems(googleMap, items);
  //        });
  //    }
}