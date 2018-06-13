package agency.tango.viking.bindings.map.managers;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import agency.tango.viking.bindings.map.models.BindablePolygon;

public class PolygonManager<T extends BindablePolygon> extends MapEntityManagerBase<T> {

  public PolygonManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  protected T addToMap(T item, GoogleMap googleMap) {
    item.setPolygon(googleMap.addPolygon(item.getPolygonOptions()));
    return item;
  }

  @Override
  protected void removeFromMap(T entity, GoogleMap googleMap) {
    entity.getPolygon().remove();
  }

  @Override
  protected void updateOnMap(T entity, T item, GoogleMap googleMap) {
    Polygon polygon = entity.getPolygon();
    PolygonOptions polygonOptions = item.getPolygonOptions();

    polygon.setFillColor(polygonOptions.getFillColor());
    polygon.setVisible(polygonOptions.isVisible());
    polygon.setStrokeWidth(polygonOptions.getStrokeWidth());
    polygon.setZIndex(polygonOptions.getZIndex());
    polygon.setClickable(polygonOptions.isClickable());
    polygon.setGeodesic(polygonOptions.isGeodesic());
    polygon.setHoles(polygonOptions.getHoles());
    polygon.setPoints(polygonOptions.getPoints());
    polygon.setStrokeColor(polygonOptions.getStrokeColor());
  }

  @Nullable
  public T retrieveBindablePolygon(Polygon polygon) {
    for (T bindablePolygon : entities) {
      if (bindablePolygon.getPolygon().equals(polygon)) {
        return bindablePolygon;
      }
    }
    return null;
  }
}
