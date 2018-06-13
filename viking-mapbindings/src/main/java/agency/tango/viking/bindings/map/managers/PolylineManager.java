package agency.tango.viking.bindings.map.managers;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import agency.tango.viking.bindings.map.models.BindablePolyline;

public class PolylineManager<T extends BindablePolyline> extends MapEntityManagerBase<T>
    implements IMapEntityManager<T> {

  public PolylineManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  protected T addToMap(T item, GoogleMap googleMap) {
    item.setPolyline(googleMap.addPolyline(item.getPolylineOptions()));
    return item;
  }

  @Override
  protected void removeFromMap(T entity, GoogleMap googleMap) {
    entity.getPolyline().remove();
  }

  @Override
  protected void updateOnMap(T entity, T item, GoogleMap googleMap) {
    Polyline polyline = entity.getPolyline();
    PolylineOptions polylineOptions = item.getPolylineOptions();

    polyline.setClickable(polylineOptions.isClickable());
    polyline.setPoints(polylineOptions.getPoints());
    polyline.setColor(polylineOptions.getColor());
    polyline.setGeodesic(polylineOptions.isGeodesic());
    polyline.setVisible(polylineOptions.isVisible());
    polyline.setWidth(polylineOptions.getWidth());
    polyline.setZIndex(polylineOptions.getZIndex());
  }

  @Nullable
  public T retrieveBindablePolyline(Polyline polyline) {
    for (T bindablePolyline : entities) {
      if (bindablePolyline.getPolyline().equals(polyline)) {
        return bindablePolyline;
      }
    }
    return null;
  }
}
