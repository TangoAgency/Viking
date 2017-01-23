package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.InfoWindowAdapterFactory;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.listeners.OnMarkerClickListener;
import agency.tango.viking.bindings.map.models.BindableMarker;

@SuppressWarnings({ "unused" })
public class MarkerBindings {
  private MarkerBindings() {
  }

  @BindingAdapter("gmv_markers")
  public static <T> void mapItems(GoogleMapView<T> googleMapView,
      Collection<BindableMarker<T>> markers) {
    if (markers == null) {
      return;
    }
    googleMapView.markers(markers);
  }

  @BindingAdapter("gmv_markerClickListener")
  public static <T> void markerClickListener(GoogleMapView<T> googleMapView,
      OnMarkerClickListener<BindableMarker<T>> markerClickListener) {
    googleMapView.setOnMarkerClickListener(markerClickListener);
  }

  @BindingAdapter("gmv_infoWindowAdapter")
  public static <T> void infoWindowAdapter(GoogleMapView googleMapView,
      InfoWindowAdapterFactory<T> infoWindowAdapter) {
    googleMapView.setInfoWindowAdapter(infoWindowAdapter);
  }

  @BindingAdapter("gmv_infoWindowClickListener")
  public static <T> void infoWindowClick(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnInfoWindowClickListener(itemClickListener);
  }

  @BindingAdapter("gmv_infoWindowCloseListener")
  public static void onInfoWindowCloseListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowCloseListener onInfoWindowCloseListener) {
    googleMapView.setOnInfoWindowCloseListener(onInfoWindowCloseListener);
  }

  @BindingAdapter("gmv_infoWindowLongClickListener")
  public static void onInfoWindowLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
    googleMapView.setOnInfoWindowLongClickListener(onInfoWindowLongClickListener);
  }

  @BindingAdapter("gmv_markerDragListener")
  public static void onMarkerDragListener(GoogleMapView googleMapView,
      GoogleMap.OnMarkerDragListener onMarkerDragListener) {
    googleMapView.setOnMarkerDragListener(onMarkerDragListener);
  }
}