package agency.tango.viking.bindings.map.bindings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.models.BindableItem;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

@SuppressWarnings({ "unused" })
public class MapBindings {
  private MapBindings() {
  }

  @BindingAdapter("gmv_bounds")
  public static void setBounds(GoogleMapView googleMapView, LatLngBounds bounds) {
    googleMapView.bounds().setValueAndDisable(bounds);
  }

  @InverseBindingAdapter(attribute = "gmv_bounds", event = "gmv_boundsChanged")
  public static LatLngBounds getBounds(GoogleMapView googleMapView) {
    return (LatLngBounds) googleMapView.bounds().getValue();
  }

  @BindingAdapter("gmv_boundsChanged")
  public static void setBoundsOnChangeListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.bounds(), bindingListener);
  }

  @BindingAdapter("gmv_radius")
  public static void setRadius(GoogleMapView googleMapView, int radius) {
    googleMapView.radius().setValueAndDisable(radius);
  }

  @InverseBindingAdapter(attribute = "gmv_radius", event = "gmv_radiusChanged")
  public static int getRadius(GoogleMapView googleMapView) {
    return (int) googleMapView.radius().getValue();
  }

  @BindingAdapter("gmv_radiusChanged")
  public static void setRadiusOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.radius(), bindingListener);
  }

  @BindingAdapter("gmv_zoom")
  public static void setZoom(GoogleMapView googleMapView, float zoom) {
    googleMapView.zoom().setValueAndDisable(zoom);
  }

  @InverseBindingAdapter(attribute = "gmv_zoom", event = "gmv_zoomChanged")
  public static float getZoom(GoogleMapView googleMapView) {
    return (float) googleMapView.zoom().getValue();
  }

  @BindingAdapter("gmv_zoomChanged")
  public static void setZoomOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.zoom(), bindingListener);
  }

  @BindingAdapter("gmv_latLng")
  public static void setLatLng(GoogleMapView googleMapView, LatLng latLng) {

    if (latLng != googleMapView.latLng().getValue()) {
      googleMapView.postChangedLocation(latLng);
    }
  }

  @InverseBindingAdapter(attribute = "gmv_latLng", event = "gmv_latLngChanged")
  public static LatLng getLatLng(GoogleMapView googleMapView) {
    return (LatLng) googleMapView.latLng().getValue();
  }

  @BindingAdapter("gmv_latLngChanged")
  public static void setLatLngChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.latLng(), bindingListener);
  }

  @BindingAdapter("gmv_heatMap")
  public static void heatRadius(GoogleMapView googleMapView,
      HeatmapTileProvider heatmapTileProvider) {
    googleMapView.heatMap(heatmapTileProvider);
  }

  @BindingAdapter("gmv_mapClickListener")
  public static void onMapClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapClickListener onMapClickListener) {
    googleMapView.setOnMapClickListener(onMapClickListener);
  }

  @BindingAdapter("gmv_mapLoadedCallback")
  public static void onMapLoadedCallback(GoogleMapView googleMapView,
      GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
    googleMapView.setOnMapLoadedCallback(onMapLoadedCallback);
  }

  @BindingAdapter("gmv_mapLongClickListener")
  public static void onMapLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMapLongClickListener onMapLongClickListener) {
    googleMapView.setOnMapLongClickListener(onMapLongClickListener);
  }

  @BindingAdapter("gmv_myLocationButtonClickListener")
  public static void onMyLocationButtonClickListener(GoogleMapView googleMapView,
      GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
    googleMapView.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
  }

  @BindingAdapter("gmv_indoorStateChangeListener")
  public static void onIndoorStateChangedListener(GoogleMapView googleMapView,
      GoogleMap.OnIndoorStateChangeListener onIndoorStateChangedListener) {
    googleMapView.setOnIndoorStateChangeListener(onIndoorStateChangedListener);
  }

  @BindingAdapter("gmv_poiClickListener")
  public static void onPoiClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPoiClickListener onPoiClickListener) {
    googleMapView.setOnPoiClickListener(onPoiClickListener);
  }

  @BindingAdapter("gmv_snapshotReadyCallback")
  public static void snapshotReadyCallback(GoogleMapView googleMapView,
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
