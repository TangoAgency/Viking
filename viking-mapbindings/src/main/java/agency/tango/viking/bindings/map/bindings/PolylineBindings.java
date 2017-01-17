package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;

public class PolylineBindings {
  private PolylineBindings() {}

  @BindingAdapter("polylines")
  public static void setPolylines(GoogleMapView googleMapView,
      Collection<PolylineOptions> polylines) {
    if (polylines == null) {
      return;
    }
    googleMapView.polylines(polylines);
  }

  @BindingAdapter("polylineClickListener")
  public static void polylineClickListener(GoogleMapView googleMapView,
      GoogleMap.OnPolylineClickListener polylineClickListener) {
    googleMapView.setOnPolylineClickListener(polylineClickListener);
  }
}
