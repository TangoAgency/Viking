package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.model.CircleOptions;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindableCircle;

public class CircleBindings {
  private CircleBindings() {}

  @BindingAdapter("circles")
  public static void circles(GoogleMapView googleMapView,
      Collection<CircleOptions> circles) {
    if (circles == null) {
      return;
    }
    googleMapView.circles(circles);
  }

  @BindingAdapter("circleClickListener")
  public static void circleClickListener(GoogleMapView googleMapView,
      ItemClickListener<BindableCircle> itemClickListener) {
    googleMapView.setOnCircleClickListener(itemClickListener);
  }
}
