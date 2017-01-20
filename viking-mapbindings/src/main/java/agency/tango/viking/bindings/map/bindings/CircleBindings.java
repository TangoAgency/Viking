package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindableCircle;

public class CircleBindings {
  private CircleBindings() {}

  @BindingAdapter("gmv_circles")
  public static void circles(GoogleMapView googleMapView,
      Collection<BindableCircle> circles) {
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
