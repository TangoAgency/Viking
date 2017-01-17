package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import agency.tango.viking.bindings.map.BindableItem;
import agency.tango.viking.bindings.map.GoogleMapView;

@SuppressWarnings({ "unused" })
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

  @BindingAdapter("latLng")
  public static void setLatLng(GoogleMapView googleMapView, LatLng latLng) {
    if (latLng != googleMapView.latLng().getValue()) {
      googleMapView.postChangedLocation(latLng);
    }
  }

  @InverseBindingAdapter(attribute = "latLng", event = "latLngChanged")
  public static LatLng getLatLng(GoogleMapView googleMapView) {
    return (LatLng) googleMapView.latLng().getValue();
  }

  @BindingAdapter("latLngChanged")
  public static void setLatLngChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.latLng(), bindingListener);
  }

  @BindingAdapter("heatMap")
  public static void heatRadius(GoogleMapView googleMapView,
      HeatmapTileProvider heatmapTileProvider) {
    googleMapView.heatMap(heatmapTileProvider);
  }

  @BindingAdapter("mapClickListener")
  public static void onMapClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapClickListener onMapClickListener) {
    googleMapView.setOnMapClickListener(onMapClickListener);
  }

  @BindingAdapter("mapLoadedCallback")
  public static void onMapLoadedCallback(GoogleMapView googleMapView,
      GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
    googleMapView.setOnMapLoadedCallback(onMapLoadedCallback);
  }

  @BindingAdapter("mapLongClickListener")
  public static void onMapLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapLongClickListener onMapLongClickListener) {
    googleMapView.setOnMapLongClickListener(onMapLongClickListener);
  }

  @BindingAdapter("myLocationButtonClickListener")
  public static void onMyLocationButtonClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
    googleMapView.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
  }

  @BindingAdapter("polygonClickListener")
  public static void polygonClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPolygonClickListener polygonClickListener) {
    googleMapView.setOnPolygonClickListener(polygonClickListener);
  }

  @BindingAdapter("circleClickListener")
  public static void onCircleClickListener(GoogleMapView googleMapView,
      GoogleMap.OnCircleClickListener onCircleClickListener) {
    googleMapView.setOnCircleClickListener(onCircleClickListener);
  }

  @BindingAdapter("indoorStateChangeListener")
  public static void onIndoorStateChangedListener(GoogleMapView googleMapView,
      GoogleMap.OnIndoorStateChangeListener onIndoorStateChangedListener) {
    googleMapView.setOnIndoorStateChangeListener(onIndoorStateChangedListener);
  }

  @BindingAdapter("poiClickListener")
  public static void onPoiClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPoiClickListener onPoiClickListener) {
    googleMapView.setOnPoiClickListener(onPoiClickListener);
  }

  @BindingAdapter("snapshotReadyCallback")
  public static void cameraIdleListener(GoogleMapView googleMapView,
      GoogleMap.SnapshotReadyCallback snapshotReadyCallback) {
    googleMapView.setSnapshotReadyCallback(snapshotReadyCallback);
  }

  private static <T> void setOnValueChangedListener(BindableItem<T> item,
      InverseBindingListener bindingListener) {
    if (bindingListener == null) {
      item.setOnChangeListener(null);
    } else {
      item.setOnChangeListener(value -> bindingListener.onChange());
    }
  }
}
