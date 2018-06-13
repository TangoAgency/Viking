package agency.tango.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindablePolyline;

@SuppressWarnings({ "unused" })
public class PolylineBindings {

  private PolylineBindings() {
  }

  @BindingAdapter("gmv_polylines")
  public static void setPolylines(GoogleMapView googleMapView,
      Collection<? extends BindablePolyline> polylines) {
    if (polylines == null) {
      return;
    }
    googleMapView.polylines(polylines);
  }

  @BindingAdapter("gmv_polylineClickListener")
  public static void polylineClickListener(GoogleMapView googleMapView,
      ItemClickListener<BindablePolyline> itemClickListener) {
    googleMapView.setOnPolylineClickListener(itemClickListener);
  }
}
