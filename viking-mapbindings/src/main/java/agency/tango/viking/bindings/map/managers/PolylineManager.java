package agency.tango.viking.bindings.map.managers;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import agency.tango.viking.bindings.map.models.BindablePolyline;

public class PolylineManager extends MapEntityManagerBase<BindablePolyline>
    implements IMapEntityManager<BindablePolyline> {

  public PolylineManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  BindablePolyline addToMap(BindablePolyline item, GoogleMap googleMap) {
    item.setPolyline(googleMap.addPolyline(item.getPolylineOptions()));
    return item;
  }

  @Override
  void removeFromMap(BindablePolyline entity, GoogleMap googleMap) {
    entity.getPolyline().remove();
  }

  @Override
  void updateOnMap(BindablePolyline entity, BindablePolyline item, GoogleMap googleMap) {
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
  public BindablePolyline retrieveBindablePolyline(Polyline polyline) {
    for (BindablePolyline bindablePolyline : entities) {
      if (bindablePolyline.getPolyline().equals(polyline)) {
        return bindablePolyline;
      }
    }
    return null;
  }
}
