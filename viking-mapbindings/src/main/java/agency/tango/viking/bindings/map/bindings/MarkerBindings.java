package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindableMarker;

public class MarkerBindings {
  private MarkerBindings() {
  }

  @BindingAdapter("mapItems")
  public static <T> void mapItems(GoogleMapView<T> googleMapView,
      Collection<BindableMarker<T>> items) {
    if (items == null) {
      return;
    }
    googleMapView.markers(items);
  }

  @BindingAdapter("markerClickListener")
  public static <T> void markerClickListener(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnMarkerClickListener(itemClickListener);
  }

  @BindingAdapter("infoWindowAdapter")
  public static void infoWindowAdapter(GoogleMapView googleMapView,
      GoogleMap.InfoWindowAdapter infoWindowAdapter) {
    googleMapView.setInfoWindowAdapter(infoWindowAdapter);
  }

  @BindingAdapter("infoWindowClickListener")
  public static <T> void infoWindowClick(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnInfoWindowClickListener(itemClickListener);
  }

  @BindingAdapter("infoWindowCloseListener")
  public static void onInfoWindowCloseListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowCloseListener onInfoWindowCloseListener) {
    googleMapView.setOnInfoWindowCloseListener(onInfoWindowCloseListener);
  }

  @BindingAdapter("infoWindowLongClickListener")
  public static void onInfoWindowLongClickListener(GoogleMapView googleMapView,
      GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
    googleMapView.setOnInfoWindowLongClickListener(onInfoWindowLongClickListener);
  }

  @BindingAdapter("markerDragListener")
  public static void onMarkerDragListener(GoogleMapView googleMapView,
      GoogleMap.OnMarkerDragListener onMarkerDragListener) {
    googleMapView.setOnMarkerDragListener(onMarkerDragListener);
  }
}