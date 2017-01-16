package agency.tango.viking.bindings.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.Collection;

import agency.tango.viking.bindings.map.clickHandlers.ItemClickListener;
import agency.tango.viking.bindings.map.listeners.ICameraIdleListener;
import agency.tango.viking.bindings.map.models.BindableMarker;
import agency.tango.viking.bindings.map.models.BindableOverlay;
import agency.tango.viking.bindings.map.models.BindablePolyline;

public class GoogleMapView<T> extends MapView {
  private ArrayList<ICameraIdleListener> cameraIdleListeners = new ArrayList<>();

  private ItemClickListener itemClickListener;
  //private ItemPopupAdapter infoWindowAdapter;

  private BindableItem<Location> location = new BindableItem<>();
  private BindableItem<Float> zoom = new BindableItem<>();
  private BindableItem<Integer> radius = new BindableItem<>();

  private MarkerManager<T> markerManager;
  private PolylineManager polylineManager;
  private OverlayManager overlayManager;

  private TileOverlay heatMapTileOverlay;
  private GoogleMap.InfoWindowAdapter infoWindowAdapter;
  private ItemClickListener<BindableMarker<T>> infoWindowClickListener;

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
    getMapAsync(googleMap -> googleMap.moveCamera(CameraUpdateFactory.newLatLng(
        new LatLng(location.getLatitude(), location.getLongitude()))));
    updateField(this.location, location);
  }

  public void infoWindowAdapter(GoogleMap.InfoWindowAdapter infoWindowAdapter) {
    getMapAsync(googleMap -> googleMap.setInfoWindowAdapter(infoWindowAdapter));
  }

  //public void popupInfoAdapter(ItemPopupAdapter infoWindowAdapter) {
  //  this.infoWindowAdapter = infoWindowAdapter;
  //  getMapAsync(googleMap -> {
  //    googleMap.setInfoWindowAdapter(infoWindowAdapter);
  //
  //    googleMap.setOnInfoWindowClickListener(marker -> {
  //      if (infoWindowAdapter != null) {
  //        infoWindowAdapter.itemClicked(markerManager.retrieveBindableMarker(marker));
  //      }
  //    });
  //  });
  //}

  public void markerClick(ItemClickListener<BindableMarker<T>> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public void infoWindowClick(ItemClickListener<BindableMarker<T>> itemClickListener) {
    this.infoWindowClickListener = itemClickListener;
  }

  public void onMarkerClick(BindableMarker item) {
    if (itemClickListener != null) {
      itemClickListener.onClick(item);
    }
  }

  //    public void markerPopupClicked(ItemClickListener clickHandler)
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

  public void markers(Collection<BindableMarker<T>> items) {
    getMapAsync(googleMap -> markerManager.addItems(googleMap, items));
  }

  public void paths(Collection<BindablePolyline> paths) {
    getMapAsync(googleMap -> polylineManager.addItems(googleMap, paths));
  }

  public void overlays(Collection<BindableOverlay> overlays) {
    getMapAsync(googleMap -> overlayManager.addItems(googleMap, overlays));
  }

  public void heatMap(HeatmapTileProvider heatmapTileProvider) {
    if (heatMapTileOverlay != null) {
      heatMapTileOverlay.remove();
    }

    getMapAsync(googleMap -> heatMapTileOverlay = googleMap.addTileOverlay(
        new TileOverlayOptions().tileProvider(heatmapTileProvider)));
  }

  private void init() {
    markerManager = new MarkerManager<>(this::getMapAsync);
    polylineManager = new PolylineManager(this::getMapAsync);
    overlayManager = new OverlayManager(this::getMapAsync);

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

      googleMap.setOnMarkerClickListener(markerClicked -> {
        BindableMarker<T> bindableMarker = markerManager.retrieveBindableMarker(markerClicked);
        if (bindableMarker != null) {
          onMarkerClick(bindableMarker);
          return false;
        }
        return true;
      });

      googleMap.setOnInfoWindowClickListener(markerClicked -> {
        BindableMarker<T> bindableMarker = markerManager.retrieveBindableMarker(markerClicked);
        if (bindableMarker != null) {
          if (infoWindowClickListener != null) {
            infoWindowClickListener.onClick(bindableMarker);
          }
        }
      });
    });
  }

  private void registerOnCameraIdleListener(ICameraIdleListener cameraIdleListener) {
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

  private <E> void updateField(BindableItem<E> item, E value) {
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
}