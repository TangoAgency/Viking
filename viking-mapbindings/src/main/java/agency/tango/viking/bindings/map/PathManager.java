package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PathManager extends MapEntityManagerBase<PolylineOptions, Polyline>
    implements IMapEntityManager<PolylineOptions> {

  public PathManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  Polyline create(PolylineOptions item, GoogleMap googleMap) {
    return googleMap.addPolyline(item);
  }

  @Override
  void remove(Polyline entity, GoogleMap googleMap) {
    entity.remove();
  }

  @Override
  void update(Polyline entity, PolylineOptions item, GoogleMap googleMap) {
    entity.setClickable(item.isClickable());
    entity.setPoints(item.getPoints());
    entity.setColor(item.getColor());
    entity.setGeodesic(item.isGeodesic());
    entity.setVisible(item.isVisible());
    entity.setWidth(item.getWidth());
    entity.setZIndex(item.getZIndex());
  }

  @Override
  public boolean compareEntities(Polyline polyline, PolylineOptions polylineOptions) {
    return polyline.getPoints().equals(polylineOptions.getPoints());
  }
}
