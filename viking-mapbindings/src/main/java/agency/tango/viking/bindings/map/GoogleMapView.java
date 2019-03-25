package agency.tango.viking.bindings.map;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterItem;
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
import agency.tango.viking.bindings.map.listeners.MarkerDragListener;
import agency.tango.viking.bindings.map.listeners.OnMarkerClickListener;
import agency.tango.viking.bindings.map.listeners.OverlayClickListener;
import agency.tango.viking.bindings.map.listeners.PolygonClickListener;
import agency.tango.viking.bindings.map.listeners.PolylineClickListener;
import agency.tango.viking.bindings.map.listeners.WindowInfoClickListener;
import agency.tango.viking.bindings.map.managers.CircleManager;
import agency.tango.viking.bindings.map.managers.ClusterItemManager;
import agency.tango.viking.bindings.map.managers.CustomClusterManager;
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
  private static final float DEFAULT_MAP_CENTER_ZOOM = 14f;

  private BindableItem<LatLng> latLng = new BindableItem<>(value -> {
    getMapAsync(googleMap -> {
      disable();

      float mapCenterZoom;
      if (zoomNotNull()) {
        mapCenterZoom = DEFAULT_MAP_CENTER_ZOOM;
      } else {
        mapCenterZoom = zoom().getValue();
      }

      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(value, mapCenterZoom));
    });
  });

  private BindableItem<Float> zoom = new BindableItem<>(value -> {
    getMapAsync(googleMap -> {
      disable();
      googleMap.moveCamera(CameraUpdateFactory.zoomTo(value));
    });
  });

  private BindableItem<LatLngBounds> bounds = new BindableItem<>(value -> {
    getMapAsync(googleMap -> {
      disable();
      googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(value, 0));
    });
  });

  private BindableItem<Integer> radius = new BindableItem<>();

  private MarkerManager<T> markerManager;
  private PolylineManager polylineManager;
  private OverlayManager overlayManager;
  private CircleManager circleManager;
  private PolygonManager polygonManager;

  private CustomClusterManager<ClusterItem> customClusterManager;

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

  public BindableItem<LatLngBounds> bounds() {
    return bounds;
  }

  public void postChangedLocation(LatLng latLng) {
    disable();
    getMapAsync(googleMap -> googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)));
    this.latLng.setValueAndDisable(latLng);

  }

  public void enable() {
    latLng.enable();
    zoom.enable();
    bounds.enable();
  }

  public void disable() {
    latLng.disable();
    zoom.disable();
    bounds.disable();
  }

  //region Listeners

  /**
   * Add on camera idle listener
   *
   * @param onCameraIdleListener {@link GoogleMap.OnCameraIdleListener} to add
   */
  public void addOnCameraIdleListener(GoogleMap.OnCameraIdleListener onCameraIdleListener) {
    this.onCameraIdleListener.addOnCameraIdleListener(onCameraIdleListener);
  }

  /**
   * Set on camera move started listener
   *
   * @param onCameraMoveStartedListener {@link GoogleMap.OnCameraMoveStartedListener} to set
   */
  public void setOnCameraMoveStartedListener(
      GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener) {
    getMapAsync(googleMap -> googleMap.setOnCameraMoveStartedListener(onCameraMoveStartedListener));
  }

  /**
   * Set on camera move canceled listener
   *
   * @param onCameraMoveCanceledListener {@link GoogleMap.OnCameraMoveCanceledListener} to set
   */
  public void setOnCameraMoveCanceledListener(
      GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
    getMapAsync(
        googleMap -> googleMap.setOnCameraMoveCanceledListener(onCameraMoveCanceledListener));
  }

  /**
   * Set on camera move listener
   *
   * @param onCameraMoveListener {@link GoogleMap.OnCameraMoveListener} to set
   */
  public void setOnCameraMoveListener(GoogleMap.OnCameraMoveListener onCameraMoveListener) {
    getMapAsync(googleMap -> googleMap.setOnCameraMoveListener(onCameraMoveListener));
  }

  /**
   * Set on indoor state change listener
   *
   * @param onIndoorStateChangeListener {@link GoogleMap.OnIndoorStateChangeListener} to set
   */
  public void setOnIndoorStateChangeListener(
      GoogleMap.OnIndoorStateChangeListener onIndoorStateChangeListener) {
    getMapAsync(googleMap -> googleMap.setOnIndoorStateChangeListener(onIndoorStateChangeListener));
  }

  /**
   * Set on info window close listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnInfoWindowCloseListener(
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    getMapAsync(googleMap -> googleMap.setOnInfoWindowCloseListener(
        marker -> itemClickListener.onClick(markerManager.retrieveBindableMarker(marker))));
  }

  /**
   * Set on info window long click listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnInfoWindowLongClickListener(
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    getMapAsync(googleMap -> googleMap.setOnInfoWindowLongClickListener(
        marker -> itemClickListener.onClick(markerManager.retrieveBindableMarker(marker))));
  }

  /**
   * Set on map click listener
   *
   * @param onMapClickListener {@link GoogleMap.OnMapClickListener} to set
   */
  public void setOnMapClickListener(GoogleMap.OnMapClickListener onMapClickListener) {
    getMapAsync(googleMap -> googleMap.setOnMapClickListener(onMapClickListener));
  }

  /**
   * Set on map loaded callback
   *
   * @param onMapLoadedCallback {@link GoogleMap.OnMapLoadedCallback} to set
   */
  public void setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
    getMapAsync(googleMap -> googleMap.setOnMapLoadedCallback(onMapLoadedCallback));
  }

  /**
   * Set on map long click listener
   *
   * @param onMapLongClickListener {@link GoogleMap.OnMapLongClickListener} to set
   */
  public void setOnMapLongClickListener(GoogleMap.OnMapLongClickListener onMapLongClickListener) {
    getMapAsync(googleMap -> googleMap.setOnMapLongClickListener(onMapLongClickListener));
  }

  /**
   * Set on my location button click listener
   *
   * @param onMyLocationButtonClickListener {@link GoogleMap.OnMyLocationButtonClickListener} to
   * set
   */
  public void setOnMyLocationButtonClickListener(
      GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
    getMapAsync(
        googleMap -> googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener));
  }

  /**
   * Set on poi click listener
   *
   * @param onPoiClickListener {@link GoogleMap.OnPoiClickListener} to set
   */
  public void setOnPoiClickListener(GoogleMap.OnPoiClickListener onPoiClickListener) {
    getMapAsync(googleMap -> googleMap.setOnPoiClickListener(onPoiClickListener));
  }

  /**
   * Set snapshot ready callback
   *
   * @param snapshotReadyCallback {@link GoogleMap.SnapshotReadyCallback} to set
   */
  public void setSnapshotReadyCallback(GoogleMap.SnapshotReadyCallback snapshotReadyCallback) {
    getMapAsync(googleMap -> googleMap.snapshot(snapshotReadyCallback));
  }
  //endregion

  //region Cluster

  /**
   * Set on clusters click listener
   *
   * @param clusterClickListener {@link ClusterManager.OnClusterClickListener} to set
   */
  public void setOnClusterClickListener(
      ClusterManager.OnClusterClickListener<ClusterItem> clusterClickListener) {
    customClusterManager.onClusterManagerReady(
        clusterManager -> clusterManager.setOnClusterClickListener(clusterClickListener));
  }

  /**
   * Set on clusters item click listener
   *
   * @param clusterItemClickListener {@link ClusterManager.OnClusterItemClickListener} to set
   */
  public void setOnClusterItemClickListener(
      ClusterManager.OnClusterItemClickListener<ClusterItem> clusterItemClickListener) {
    customClusterManager.onClusterManagerReady(
        clusterManager -> clusterManager.setOnClusterItemClickListener(clusterItemClickListener));
  }

  /**
   * Set on cluster info window click listener
   *
   * @param clusterInfoWindowClickListener {@link ClusterManager.OnClusterInfoWindowClickListener}
   * to set
   */
  public void setOnClusterInfoWindowClickListener(
      ClusterManager.OnClusterInfoWindowClickListener<ClusterItem> clusterInfoWindowClickListener) {
    customClusterManager.onClusterManagerReady(clusterManager ->
        clusterManager.setOnClusterInfoWindowClickListener(clusterInfoWindowClickListener));
  }

  /**
   * Set on cluster item info window click listener
   *
   * @param clusterItemInfoWindowClickListener {@link ClusterManager.OnClusterItemInfoWindowClickListener}
   * to set
   */
  public void setOnClusterItemInfoWindowClickListener(
      ClusterManager.OnClusterItemInfoWindowClickListener<ClusterItem> clusterItemInfoWindowClickListener) {
    customClusterManager.onClusterManagerReady(clusterManager ->
        clusterManager.setOnClusterItemInfoWindowClickListener(clusterItemInfoWindowClickListener));
  }

  /**
   * Set algorithm for clustering
   *
   * @param algorithm {@link Algorithm} to set
   */
  public void setAlgorithm(Algorithm<ClusterItem> algorithm) {
    customClusterManager.onClusterManagerReady(clusterManager ->
        clusterManager.setAlgorithm(algorithm));
  }

  /**
   * Set info window adapter on cluster item
   *
   * @param infoWindowAdapterFactory {@link InfoWindowAdapterFactory} to set
   */
  public void setClusterItemInfoWindowAdapter(
      InfoWindowAdapterFactory<ClusterItem> infoWindowAdapterFactory) {
    customClusterManager.onClusterManagerReady(clusterManager -> {
      ClusterItemWindowInfoAdapter<ClusterItem> adapter = new ClusterItemWindowInfoAdapter<>(
          infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), clusterManager);

      clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(adapter);
      infoWindowAdapter.addInfoWindowAdapter(adapter);
    });
  }

  /**
   * Set info window adapter on cluster
   *
   * @param infoWindowAdapterFactory {@link InfoWindowAdapterFactory} to set
   */
  public void setClusterInfoWindowAdapter(
      InfoWindowAdapterFactory infoWindowAdapterFactory) {
    customClusterManager.onClusterManagerReady(clusterManager -> {
      ClusterWindowInfoAdapter adapter = new ClusterWindowInfoAdapter(
          infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), clusterManager);

      clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(adapter);
      infoWindowAdapter.addInfoWindowAdapter(adapter);
    });
  }

  /**
   * Set renderer for cluster items
   *
   * @param rendererFactory {@link RendererFactory} to set
   */
  public void setRendererFactory(RendererFactory<ClusterItem> rendererFactory) {
    getMapAsync(googleMap -> customClusterManager.onClusterManagerReady(
        clusterManager -> clusterManager.setRenderer(
            rendererFactory.createRenderer(getContext(), googleMap, clusterManager))));
  }

  /**
   * Set cluster items to be displayed on map view
   *
   * @param clusterItems Collection of {@link ClusterItem} to set
   */
  public void clusterItems(Collection<ClusterItem> clusterItems) {
    getMapAsync(googleMap -> customClusterManager.onClusterManagerReady(clusterManager -> {
      ClusterItemManager<ClusterItem> clusterItemManager = new ClusterItemManager<>(
          this::getMapAsync, clusterManager);
      onCameraIdleListener.addOnCameraIdleListener(clusterManager);
      markerClickListener.addOnMarkerClickListener(clusterManager);
      infoWindowClickListener.addOnInfoWindowClickListener(clusterManager);

      clusterItemManager.addItems(googleMap, clusterItems);
    }));
  }
  //endregion

  //region Markers

  /**
   * Set markers to be displayed on map view
   *
   * @param markers Collection of {@link BindableMarker} to set
   */
  public void markers(Collection<BindableMarker<T>> markers) {
    getMapAsync(googleMap -> markerManager.addItems(googleMap, markers));
  }

  /**
   * Set on marker click listener
   *
   * @param markerItemClickListener {@link OnMarkerClickListener} to set
   */
  public void setOnMarkerClickListener(
      OnMarkerClickListener<BindableMarker<T>> markerItemClickListener) {
    markerClickListener.addOnMarkerClickListener(
        new MarkerClickListener<>(markerItemClickListener, markerManager));
  }

  /**
   * Set on marker drag listener
   *
   * @param markerDragListener {@link MarkerDragListener} to set
   */
  public void setMarkerDragListener(MarkerDragListener<T> markerDragListener) {
    getMapAsync(googleMap -> googleMap.setOnMarkerDragListener(
        new GoogleMap.OnMarkerDragListener() {
          @Override
          public void onMarkerDragStart(Marker marker) {
            markerDragListener.onMarkerDragStart(markerManager.retrieveBindableMarker(marker));
          }

          @Override
          public void onMarkerDrag(Marker marker) {
            markerDragListener.onMarkerDrag(markerManager.retrieveBindableMarker(marker));

          }

          @Override
          public void onMarkerDragEnd(Marker marker) {
            markerDragListener.onMarkerDragEnd(markerManager.retrieveBindableMarker(marker));
          }
        }));
  }

  /**
   * Set info window adapter for markers
   *
   * @param infoWindowAdapterFactory {@link InfoWindowAdapterFactory} to set
   */
  public void setInfoWindowAdapter(
      InfoWindowAdapterFactory<BindableMarker<T>> infoWindowAdapterFactory) {
    getMapAsync(googleMap -> {
      MarkerInfoWindowAdapter<T> adapter = new MarkerInfoWindowAdapter<>(
          infoWindowAdapterFactory.createInfoWindowAdapter(getContext()), markerManager);

      infoWindowAdapter.addInfoWindowAdapter(adapter);
    });
  }

  /**
   * Set on info window click listener
   *
   * @param infoWindowClickListener {@link ItemClickListener} to set
   */
  public void setOnInfoWindowClickListener(
      ItemClickListener<BindableMarker<T>> infoWindowClickListener) {
    this.infoWindowClickListener.addOnInfoWindowClickListener(
        new WindowInfoClickListener<>(infoWindowClickListener, markerManager));
  }
  //endregion

  //region Polylines

  /**
   * Set polylines to be displayed on map view
   *
   * @param polylines Collection of {@link BindablePolyline} to set
   */
  public void polylines(Collection<? extends BindablePolyline> polylines) {
    getMapAsync(googleMap -> polylineManager.addItems(googleMap, polylines));
  }

  /**
   * Set on polyline click listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnPolylineClickListener(ItemClickListener<BindablePolyline> itemClickListener) {
    polylineClickListener.setItemClickListener(itemClickListener);
  }
  //endregion

  //region GroundOverlays

  /**
   * Set overlays to be displayed on map view
   *
   * @param overlays Collection of {@link BindableOverlay} to set
   */
  public void groundOverlays(Collection<? extends BindableOverlay> overlays) {
    getMapAsync(googleMap -> overlayManager.addItems(googleMap, overlays));
  }

  /**
   * Set on ground overlay click listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnGroundOverlayClickListener(
      ItemClickListener<BindableOverlay> itemClickListener) {
    overlayClickListener.setItemClickListener(itemClickListener);
  }
  //endregion

  //region Circles

  /**
   * Set circles to be displayed on map view
   *
   * @param circles Collection of {@link BindableCircle} to set
   */
  public void circles(Collection<? extends BindableCircle> circles) {
    getMapAsync(googleMap -> circleManager.addItems(googleMap, circles));
  }

  /**
   * Set on circle click listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnCircleClickListener(ItemClickListener<BindableCircle> itemClickListener) {
    circleClickListener.setItemClickListener(itemClickListener);
  }
  //endregion

  //region Polygons

  /**
   * Set polygons to be displayed on map view
   *
   * @param polygons Collection of {@link BindablePolygon} to set
   */
  public void polygons(Collection<? extends BindablePolygon> polygons) {
    getMapAsync(googleMap -> polygonManager.addItems(googleMap, polygons));
  }

  /**
   * Set on polygon click listener
   *
   * @param itemClickListener {@link ItemClickListener} to set
   */
  public void setOnPolygonClickListener(ItemClickListener<BindablePolygon> itemClickListener) {
    polygonClickListener.setItemClickListener(itemClickListener);
  }
  //endregion

  /**
   * Set heatMapTileProvider to be displayed on map view
   *
   * @param heatmapTileProvider {@link HeatmapTileProvider} to set
   */
  public void heatMap(HeatmapTileProvider heatmapTileProvider) {
    if (heatMapTileOverlay != null) {
      heatMapTileOverlay.remove();
    }

    getMapAsync(googleMap -> heatMapTileOverlay = googleMap.addTileOverlay(
        new TileOverlayOptions().tileProvider(heatmapTileProvider)));
  }

  private void init() {
    initializeManagers();
    initializeListeners();

    getMapAsync(googleMap -> {
      setListenersToGoogleMap(googleMap);

      onCameraIdleListener.addOnCameraIdleListener(() -> {
        latLng.setValue(getLatLng(googleMap));
        bounds.setValue(googleMap.getProjection().getVisibleRegion().latLngBounds);
        zoom.setValue(googleMap.getCameraPosition().zoom);
        radius.setValue(currentRadius(googleMap));
      });
    });
  }

  private void initializeManagers() {
    markerManager = new MarkerManager<>(this::getMapAsync);
    polylineManager = new PolylineManager(this::getMapAsync);
    overlayManager = new OverlayManager(this::getMapAsync);
    circleManager = new CircleManager(this::getMapAsync);
    polygonManager = new PolygonManager(this::getMapAsync);
    customClusterManager = new CustomClusterManager<>(getContext(), this::getMapAsync);
  }

  private void initializeListeners() {
    onCameraIdleListener = new CompositeOnCameraIdleListener();

    onCameraIdleListener.addOnCameraIdleListener(() -> post(this::enable));

    infoWindowAdapter = new CompositeInfoWindowAdapter();
    markerClickListener = new CompositeMarkerClickListener();
    infoWindowClickListener = new CompositeInfoWindowClickListener();
    polylineClickListener = new PolylineClickListener(polylineManager);
    overlayClickListener = new OverlayClickListener(overlayManager);
    circleClickListener = new CircleClickListener(circleManager);
    polygonClickListener = new PolygonClickListener(polygonManager);
  }

  private void setListenersToGoogleMap(GoogleMap googleMap) {
    googleMap.setInfoWindowAdapter(infoWindowAdapter);
    googleMap.setOnCameraIdleListener(onCameraIdleListener);
    googleMap.setOnMarkerClickListener(markerClickListener);
    googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
    googleMap.setOnPolylineClickListener(polylineClickListener);
    googleMap.setOnGroundOverlayClickListener(overlayClickListener);
    googleMap.setOnCircleClickListener(circleClickListener);
    googleMap.setOnPolygonClickListener(polygonClickListener);
  }

  private Boolean zoomNotNull() {
    return zoom() == null || zoom().getValue() == null;
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