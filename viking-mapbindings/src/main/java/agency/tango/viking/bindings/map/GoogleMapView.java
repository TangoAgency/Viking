package agency.tango.viking.bindings.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.Algorithm;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.Collection;

import agency.tango.viking.bindings.map.adapters.ClusterItemWindowInfoAdapter;
import agency.tango.viking.bindings.map.adapters.ClusterWindowInfoAdapter;
import agency.tango.viking.bindings.map.adapters.CompositeInfoWindowAdapter;
import agency.tango.viking.bindings.map.adapters.MarkerInfoWindowAdapter;
import agency.tango.viking.bindings.map.listeners.CircleClickListener;
import agency.tango.viking.bindings.map.listeners.CompositeInfoWindowClickListener;
import agency.tango.viking.bindings.map.listeners.CompositeMarkerClickListener;
import agency.tango.viking.bindings.map.listeners.CompositeOnCameraIdleListener;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.listeners.MarkerClickListener;
import agency.tango.viking.bindings.map.listeners.OnMarkerClickListener;
import agency.tango.viking.bindings.map.listeners.OverlayClickListener;
import agency.tango.viking.bindings.map.listeners.PolygonClickListener;
import agency.tango.viking.bindings.map.listeners.PolylineClickListener;
import agency.tango.viking.bindings.map.listeners.WindowInfoClickListener;
import agency.tango.viking.bindings.map.managers.CircleManager;
import agency.tango.viking.bindings.map.managers.ClusterItemManager;
import agency.tango.viking.bindings.map.managers.MarkerManager;
import agency.tango.viking.bindings.map.managers.OverlayManager;
import agency.tango.viking.bindings.map.managers.PolygonManager;
import agency.tango.viking.bindings.map.managers.PolylineManager;
import agency.tango.viking.bindings.map.models.BindableCircle;
import agency.tango.viking.bindings.map.models.BindableItem;
import agency.tango.viking.bindings.map.models.BindableMarker;
import agency.tango.viking.bindings.map.models.BindableOverlay;
import agency.tango.viking.bindings.map.models.BindablePolygon;
import agency.tango.viking.bindings.map.models.BindablePolyline;

public class GoogleMapView<T> extends MapView {

  private BindableItem<LatLng> latLng = new BindableItem<>();
  private BindableItem<Float> zoom = new BindableItem<>();
  private BindableItem<Integer> radius = new BindableItem<>();

  private MarkerManager<T> markerManager;
  private PolylineManager polylineManager;
  private OverlayManager overlayManager;
  private CircleManager circleManager;
  private PolygonManager polygonManager;

  private ClusterManager<ClusterMapItem> clusterManager;
  private ClusterItemManager<ClusterMapItem> clusterItemManager;

  private TileOverlay heatMapTileOverlay;

  private CompositeOnCameraIdleListener onCameraIdleListener;
  private CompositeInfoWindowAdapter infoWindowAdapter;
  private CompositeMarkerClickListener markerClickListener;
  private CompositeInfoWindowClickListener infoWindowClickListener;
  private PolylineClickListener polylineClickListener;
  private OverlayClickListener overlayClickListener;
  private CircleClickListener circleClickListener;
  private PolygonClickListener polygonClickListener;

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

  public BindableItem<LatLng> latLng() {
    return latLng;
  }

  public BindableItem<Float> zoom() {
    return zoom;
  }

  //region Listeners
  public void setOnMarkerClickListener(
      OnMarkerClickListener<BindableMarker<T>> markerItemClickListener) {
    markerClickListener.addOnMarkerClickListener(
        new MarkerClickListener<>(markerItemClickListener, markerManager));
  }

  public void setOnInfoWindowClickListener(
      ItemClickListener<BindableMarker<T>> infoWindowClickListener) {
    this.infoWindowClickListener.addOnInfoWindowClickListener(
        new WindowInfoClickListener<>(infoWindowClickListener, markerManager));
  }

  public void setOnPolylineClickListener(ItemClickListener<BindablePolyline> itemClickListener) {
    polylineClickListener.setItemClickListener(itemClickListener);
  }

  public void setOnGroundOverlayClickListener(
      ItemClickListener<BindableOverlay> itemClickListener) {
    overlayClickListener.setItemClickListener(itemClickListener);
  }

  public void setOnCircleClickListener(ItemClickListener<BindableCircle> itemClickListener) {
    circleClickListener.setItemClickListener(itemClickListener);
  }

  public void setOnPolygonClickListener(ItemClickListener<BindablePolygon> itemClickListener) {
    polygonClickListener.setItemClickListener(itemClickListener);
  }

  public void setOnCameraMoveStartedListener(
      GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener) {
    getMapAsync(googleMap -> googleMap.setOnCameraMoveStartedListener(onCameraMoveStartedListener));
  }

  public void setOnCameraMoveCanceledListener(
      GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
    getMapAsync(
        googleMap -> googleMap.setOnCameraMoveCanceledListener(onCameraMoveCanceledListener));
  }

  public void setOnCameraIdleListener(GoogleMap.OnCameraIdleListener onCameraIdleListener) {
    this.onCameraIdleListener.addOnCameraIdleListener(onCameraIdleListener);
  }

  public void setOnCameraMoveListener(GoogleMap.OnCameraMoveListener onCameraMoveListener) {
    getMapAsync(googleMap -> googleMap.setOnCameraMoveListener(onCameraMoveListener));
  }

  public void setOnIndoorStateChangeListener(
      GoogleMap.OnIndoorStateChangeListener onIndoorStateChangeListener) {
    getMapAsync(googleMap -> googleMap.setOnIndoorStateChangeListener(onIndoorStateChangeListener));
  }

  public void setOnInfoWindowCloseListener(
      GoogleMap.OnInfoWindowCloseListener onInfoWindowCloseListener) {
    getMapAsync(googleMap -> googleMap.setOnInfoWindowCloseListener(onInfoWindowCloseListener));
  }

  public void setOnInfoWindowLongClickListener(
      GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
    getMapAsync(
        googleMap -> googleMap.setOnInfoWindowLongClickListener(onInfoWindowLongClickListener));
  }

  public void setOnMapClickListener(GoogleMap.OnMapClickListener onMapClickListener) {
    getMapAsync(googleMap -> googleMap.setOnMapClickListener(onMapClickListener));
  }

  public void setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
    getMapAsync(googleMap -> googleMap.setOnMapLoadedCallback(onMapLoadedCallback));
  }

  public void setOnMapLongClickListener(GoogleMap.OnMapLongClickListener onMapLongClickListener) {
    getMapAsync(googleMap -> googleMap.setOnMapLongClickListener(onMapLongClickListener));
  }

  public void setOnMarkerDragListener(GoogleMap.OnMarkerDragListener onMarkerDragListener) {
    getMapAsync(googleMap -> googleMap.setOnMarkerDragListener(onMarkerDragListener));
  }

  public void setOnMyLocationButtonClickListener(
      GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
    getMapAsync(
        googleMap -> googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener));
  }

  public void setOnPoiClickListener(GoogleMap.OnPoiClickListener onPoiClickListener) {
    getMapAsync(googleMap -> googleMap.setOnPoiClickListener(onPoiClickListener));
  }

  public void setSnapshotReadyCallback(GoogleMap.SnapshotReadyCallback snapshotReadyCallback) {
    getMapAsync(googleMap -> googleMap.snapshot(snapshotReadyCallback));
  }
  //endregion

  //region Cluster
  public void setOnClusterClickListener(
      ClusterManager.OnClusterClickListener<ClusterMapItem> clusterClickListener) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setOnClusterClickListener(clusterClickListener);
      }
    });
  }

  public void setOnClusterItemClickListener(
      ClusterManager.OnClusterItemClickListener<ClusterMapItem> clusterItemClickListener) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setOnClusterItemClickListener(clusterItemClickListener);
      }
    });
  }

  public void setOnClusterInfoWindowClickListener(
      ClusterManager.OnClusterInfoWindowClickListener<ClusterMapItem> clusterInfoWindowClickListener) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setOnClusterInfoWindowClickListener(clusterInfoWindowClickListener);
      }
    });
  }

  public void setOnClusterItemInfoWindowClickListener(
      ClusterManager.OnClusterItemInfoWindowClickListener<ClusterMapItem> clusterItemInfoWindowClickListener) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setOnClusterItemInfoWindowClickListener(clusterItemInfoWindowClickListener);
      }
    });
  }

  public void setAlgorithm(Algorithm<ClusterMapItem> algorithm) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setAlgorithm(algorithm);
      }
    });
  }

  public void setClusterItemInfoWindowAdapter(
      InfoWindowAdapterFactory infoWindowAdapterFactory) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        ClusterItemWindowInfoAdapter adapter = new ClusterItemWindowInfoAdapter(
            infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), clusterManager);

        clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(adapter);
        infoWindowAdapter.addInfoWindowAdapter(adapter);
      }
    });
  }

  public void setClusterInfoWindowAdapter(InfoWindowAdapterFactory infoWindowAdapterFactory) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        ClusterWindowInfoAdapter adapter = new ClusterWindowInfoAdapter(
            infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), clusterManager);

        clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(adapter);
        infoWindowAdapter.addInfoWindowAdapter(adapter);
      }
    });
  }

  public void setRendererFactory(RendererFactory<ClusterMapItem> rendererFactory) {
    getMapAsync(googleMap -> {
      if (clusterManager != null) {
        clusterManager.setRenderer(
            rendererFactory.createRenderer(getContext(), googleMap, clusterManager));
      }
    });
  }

  public void clusterItems(Collection<ClusterMapItem> clusterItems) {
    getMapAsync(googleMap -> {
      if (clusterManager == null) {
        clusterManager = new ClusterManager<>(getContext(), googleMap);
        clusterItemManager = new ClusterItemManager<>(this::getMapAsync, clusterManager);
        onCameraIdleListener.addOnCameraIdleListener(() -> clusterManager.onCameraIdle());
        markerClickListener.addOnMarkerClickListener(
            marker -> clusterManager.onMarkerClick(marker));
        infoWindowClickListener.addOnInfoWindowClickListener(
            marker -> clusterManager.onInfoWindowClick(marker));
      }
      clusterItemManager.addItems(googleMap, clusterItems);
    });
  }
  //endregion

  public void markers(Collection<BindableMarker<T>> markers) {
    getMapAsync(googleMap -> markerManager.addItems(googleMap, markers));
  }

  public void polylines(Collection<BindablePolyline> polylines) {
    getMapAsync(googleMap -> polylineManager.addItems(googleMap, polylines));
  }

  public void groundOverlays(Collection<BindableOverlay> overlays) {
    getMapAsync(googleMap -> overlayManager.addItems(googleMap, overlays));
  }

  public void circles(Collection<BindableCircle> circles) {
    getMapAsync(googleMap -> circleManager.addItems(googleMap, circles));
  }

  public void polygons(Collection<BindablePolygon> polygons) {
    getMapAsync(googleMap -> polygonManager.addItems(googleMap, polygons));
  }

  public void heatMap(HeatmapTileProvider heatmapTileProvider) {
    if (heatMapTileOverlay != null) {
      heatMapTileOverlay.remove();
    }

    getMapAsync(googleMap -> heatMapTileOverlay = googleMap.addTileOverlay(
        new TileOverlayOptions().tileProvider(heatmapTileProvider)));
  }

  public void setInfoWindowAdapter(
      InfoWindowAdapterFactory<BindableMarker<T>> infoWindowAdapterFactory) {
    getMapAsync(googleMap -> {
      MarkerInfoWindowAdapter<T> adapter = new MarkerInfoWindowAdapter<>(
          infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), markerManager);

      infoWindowAdapter.addInfoWindowAdapter(adapter);
    });
  }

  private void init() {
    markerManager = new MarkerManager<>(this::getMapAsync);
    polylineManager = new PolylineManager(this::getMapAsync);
    overlayManager = new OverlayManager(this::getMapAsync);
    circleManager = new CircleManager(this::getMapAsync);
    polygonManager = new PolygonManager(this::getMapAsync);

    onCameraIdleListener = new CompositeOnCameraIdleListener();
    infoWindowAdapter = new CompositeInfoWindowAdapter();
    markerClickListener = new CompositeMarkerClickListener();
    infoWindowClickListener = new CompositeInfoWindowClickListener();
    polylineClickListener = new PolylineClickListener(polylineManager);
    overlayClickListener = new OverlayClickListener(overlayManager);
    circleClickListener = new CircleClickListener(circleManager);
    polygonClickListener = new PolygonClickListener(polygonManager);

    getMapAsync(googleMap -> {
      initGoogleMap();

      googleMap.setOnCameraIdleListener(onCameraIdleListener);
      googleMap.setInfoWindowAdapter(infoWindowAdapter);
      googleMap.setOnMarkerClickListener(markerClickListener);
      googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
      googleMap.setOnPolylineClickListener(polylineClickListener);
      googleMap.setOnGroundOverlayClickListener(overlayClickListener);
      googleMap.setOnCircleClickListener(circleClickListener);
      googleMap.setOnPolygonClickListener(polygonClickListener);

      onCameraIdleListener.addOnCameraIdleListener(() -> {
        updateField(latLng, getLatLng(googleMap));
        updateField(zoom, googleMap.getCameraPosition().zoom);
        updateField(radius, currentRadius(googleMap));
      });
    });
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

  public void postChangedLocation(LatLng latLng) {
    getMapAsync(googleMap -> googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)));
    updateField(this.latLng, latLng);
  }

  private LatLng getLatLng(GoogleMap googleMap) {
    return googleMap.getCameraPosition().target;
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