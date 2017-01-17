package agency.tango.viking.bindings.map;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.Collection;

import agency.tango.viking.bindings.map.adapters.IClusterItemAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ClusterClickHandler;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickListener;

@SuppressWarnings({ "unchecked", "unused" })
public class MapBindings {
  private MapBindings() {
  }

  @BindingAdapter("radius")
  public static void setRadius(GoogleMapView googleMapView, int radius) {
    googleMapView.radius().setValue(radius);
  }

  @InverseBindingAdapter(attribute = "radius", event = "radiusChanged")
  public static int getRadius(GoogleMapView googleMapView) {
    return (int) googleMapView.radius().getValue();
  }

  @BindingAdapter("radiusChanged")
  public static void setRadiusOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.radius(), bindingListener);
  }

  @BindingAdapter("zoom")
  public static void setZoom(GoogleMapView googleMapView, float zoom) {
    googleMapView.zoom().setValue(zoom);
  }

  @InverseBindingAdapter(attribute = "zoom", event = "zoomChanged")
  public static float getZoom(GoogleMapView googleMapView) {
    return (float) googleMapView.zoom().getValue();
  }

  @BindingAdapter("zoomChanged")
  public static void setZoomOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.zoom(), bindingListener);
  }

  @BindingAdapter("location")
  public static void setLocation(GoogleMapView googleMapView, Location location) {
    if (location != googleMapView.location().getValue()) {
      googleMapView.postChangedLocation(location);
    }
  }

  @InverseBindingAdapter(attribute = "location", event = "locationChanged")
  public static Location getLocation(GoogleMapView googleMapView) {
    return (Location) googleMapView.location().getValue();
  }

  @BindingAdapter("locationChanged")
  public static void setLocationChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.location(), bindingListener);
  }

  @BindingAdapter("markerClick")
  public static void markerClick(GoogleMapView googleMapView, ItemClickListener itemClickListener) {
    googleMapView.markerClick(itemClickListener);
  }

  @BindingAdapter("infoWindowAdapter")
  public static void infoWindowAdapter(GoogleMapView googleMapView,
      GoogleMap.InfoWindowAdapter infoWindowAdapter) {
    googleMapView.infoWindowAdapter(infoWindowAdapter);
  }

  @BindingAdapter("infoWindowClick")
  public static void infoWindowClick(GoogleMapView googleMapView,
      ItemClickListener itemClickListener) {
    googleMapView.infoWindowClick(itemClickListener);
  }

  @BindingAdapter("polylines")
  public static void setPolylines(GoogleMapView googleMapView,
      Collection<PolylineOptions> polylines) {
    if (polylines == null) {
      return;
    }
    googleMapView.polylines(polylines);
  }

  @BindingAdapter("overlays")
  public static void setOverlays(GoogleMapView googleMapView,
      Collection<GroundOverlayOptions> overlays) {
    if (overlays == null) {
      return;
    }

    googleMapView.overlays(overlays);
  }

  @BindingAdapter("mapClusteringAdapter")
  public static void mapClusteringAdapter(GoogleMapView googleMapView,
      IClusterItemAdapter adapter) {
    //        customMarkerMap.clusterAdapter(adapter);
  }

  @BindingAdapter("mapItems")
  public static <T> void mapItems(GoogleMapView googleMapView, Collection<T> items) {
    if (items == null) {
      return;
    }
    googleMapView.markers(items);
  }

  @BindingAdapter("clusterClick")
  public static void clusterClick(GoogleMapView googleMapView,
      ClusterClickHandler clusterClickHandler) {
    //        clusteredMap.clusterClicked(clusterClickHandler);
  }

  @BindingAdapter("heatMap")
  public static void heatRadius(GoogleMapView googleMapView,
      HeatmapTileProvider heatmapTileProvider) {
    googleMapView.heatMap(heatmapTileProvider);
  }

  @BindingAdapter("polylineClickListener")
  public static void polylineClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPolylineClickListener polylineClickListener) {
    googleMapView.polylineClickListener(polylineClickListener);
  }

  @BindingAdapter("polygonClickListener")
  public static void polygonClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPolygonClickListener polygonClickListener) {
    googleMapView.polygonClickListener(polygonClickListener);
  }

  @BindingAdapter("cameraMoveStartedListener")
  public static void cameraMoveStartedListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveStartedListener cameraMoveStartedListener) {
    googleMapView.cameraMoveStartedListener(cameraMoveStartedListener);
  }

  @BindingAdapter("cameraMoveCanceledListener")
  public static void cameraMoveCanceledListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveCanceledListener cameraMoveCanceledListener) {
    googleMapView.cameraMoveCanceledListener(cameraMoveCanceledListener);
  }

  @BindingAdapter("cameraIdleListener")
  public static void cameraIdleListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraIdleListener cameraIdleListener) {
    googleMapView.cameraIdleListener(cameraIdleListener);
  }

  @BindingAdapter("onCameraMoveListener")
  public static void onCameraMoveListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveListener onCameraMoveListener) {
    googleMapView.onCameraMoveListener(onCameraMoveListener);
  }

  @BindingAdapter("onCircleClickListener")
  public static void onCircleClickListener(GoogleMapView googleMapView,
      GoogleMap.OnCircleClickListener onCircleClickListener) {
    googleMapView.onCircleClickListener(onCircleClickListener);
  }

  @BindingAdapter("onGroundOverlayClickListener")
  public static void onGroundOverlayClickListener(GoogleMapView googleMapView,
      GoogleMap.OnGroundOverlayClickListener onGroundOverlayClickListener) {
    googleMapView.onGroundOverlayClickListener(onGroundOverlayClickListener);
  }

  @BindingAdapter("onIndoorStateChangedListener")
  public static void onIndoorStateChangedListener(GoogleMapView googleMapView,
      GoogleMap.OnIndoorStateChangeListener onIndoorStateChangedListener) {
    googleMapView.onIndoorStateChangedListener(onIndoorStateChangedListener);
  }

  @BindingAdapter("onInfoWindowCloseListener")
  public static void onInfoWindowCloseListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowCloseListener onInfoWindowCloseListener) {
    googleMapView.onInfoWindowCloseListener(onInfoWindowCloseListener);
  }

  @BindingAdapter("onInfoWindowLongClickListener")
  public static void onInfoWindowLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
    googleMapView.onInfoWindowLongClickListener(onInfoWindowLongClickListener);
  }

  @BindingAdapter("onMapClickListener")
  public static void onMapClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapClickListener onMapClickListener) {
    googleMapView.onMapClickListener(onMapClickListener);
  }

  @BindingAdapter("onMapLoadedCallback")
  public static void onMapLoadedCallback(GoogleMapView googleMapView,
      GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
    googleMapView.onMapLoadedCallback(onMapLoadedCallback);
  }

  @BindingAdapter("onMapLongClickListener")
  public static void onMapLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapLongClickListener onMapLongClickListener) {
    googleMapView.onMapLongClickListener(onMapLongClickListener);
  }

  @BindingAdapter("onMarkerDragListener")
  public static void onMarkerDragListener(GoogleMapView googleMapView,
      GoogleMap.OnMarkerDragListener onMarkerDragListener) {
    googleMapView.onMarkerDragListener(onMarkerDragListener);
  }

  @BindingAdapter("onMyLocationButtonClickListener")
  public static void onMyLocationButtonClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
    googleMapView.onMyLocationButtonClickListener(onMyLocationButtonClickListener);
  }

  @BindingAdapter("onPoiClickListener")
  public static void onPoiClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPoiClickListener onPoiClickListener) {
    googleMapView.onPoiClickListener(onPoiClickListener);
  }

  @BindingAdapter("snapshot")
  public static void cameraIdleListener(GoogleMapView googleMapView,
      GoogleMap.SnapshotReadyCallback snapshotReadyCallback) {
    googleMapView.snapshot(snapshotReadyCallback);
  }

  private static void setOnValueChangedListener(BindableItem item,
      InverseBindingListener bindingListener) {
    if (bindingListener == null) {
      item.setOnChangeListener(null);
    } else {
      item.setOnChangeListener(value -> bindingListener.onChange());
    }
  }
}
