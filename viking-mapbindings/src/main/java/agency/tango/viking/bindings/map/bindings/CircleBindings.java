package agency.tango.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindableCircle;

@SuppressWarnings({ "unused" })
public class CircleBindings {
  private CircleBindings() {

  }

  @BindingAdapter("gmv_circles")
  public static void circles(GoogleMapView googleMapView,
      Collection<? extends BindableCircle> circles) {
    if (circles == null) {
      return;
    }
    googleMapView.circles(circles);
  }

  @BindingAdapter("gmv_circleClickListener")
  public static void circleClickListener(GoogleMapView googleMapView,
      ItemClickListener<BindableCircle> itemClickListener) {
    googleMapView.setOnCircleClickListener(itemClickListener);
  }
}
