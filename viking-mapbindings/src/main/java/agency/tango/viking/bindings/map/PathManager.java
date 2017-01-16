package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import agency.tango.viking.bindings.map.models.BindablePolyline;

public class PathManager extends MapEntityManagerBase<BindablePolyline>
    implements IMapEntityManager<BindablePolyline> {

  public PathManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  BindablePolyline create(BindablePolyline item, GoogleMap googleMap) {
    item.setPolyline(googleMap.addPolyline(item.getPolylineOptions()));
    return item;
  }

  @Override
  void remove(BindablePolyline entity, GoogleMap googleMap) {
    entity.getPolyline().remove();
  }

  @Override
  void update(BindablePolyline entity, BindablePolyline item, GoogleMap googleMap) {
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
}
