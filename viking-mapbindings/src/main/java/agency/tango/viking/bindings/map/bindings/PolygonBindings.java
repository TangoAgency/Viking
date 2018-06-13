package agency.tango.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindablePolygon;

@SuppressWarnings({ "unused" })
public class PolygonBindings {
  private PolygonBindings() {
  }

  @BindingAdapter("gmv_polygons")
  public static void setPolygons(GoogleMapView googleMapView,
      Collection<BindablePolygon> polygons) {
    if (polygons == null) {
      return;
    }
    googleMapView.polygons(polygons);
  }

  @BindingAdapter("gmv_polygonClickListener")
  public static void polygonClickListener(GoogleMapView googleMapView,
      ItemClickListener<? extends BindablePolygon> itemClickListener) {
    googleMapView.setOnPolygonClickListener(itemClickListener);
  }
}
