package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindablePolygon;

public class PolygonBindings {
  private PolygonBindings() {
  }

  @BindingAdapter("polygons")
  public static void setPolygons(GoogleMapView googleMapView,
      Collection<PolygonOptions> polygons) {
    if (polygons == null) {
      return;
    }
    googleMapView.polygons(polygons);
  }

  @BindingAdapter("polygonClickListener")
  public static void polygonClickListener(GoogleMapView googleMapView,
      ItemClickListener<BindablePolygon> itemClickListener) {
    googleMapView.setOnPolygonClickListener(itemClickListener);
  }
}
