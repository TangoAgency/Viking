package agency.tango.viking.bindings.map.managers;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

import agency.tango.viking.bindings.map.models.BindableCircle;

public class CircleManager extends MapEntityManagerBase<BindableCircle> {
  public CircleManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  BindableCircle addToMap(BindableCircle item, GoogleMap googleMap) {
    item.setCircle(googleMap.addCircle(item.getCircleOptions()));
    return item;
  }

  @Override
  void removeFromMap(BindableCircle entity, GoogleMap googleMap) {
    entity.getCircle().remove();
  }

  @Override
  void updateOnMap(BindableCircle entity, BindableCircle item, GoogleMap googleMap) {
    Circle circle = entity.getCircle();
    CircleOptions circleOptions = item.getCircleOptions();

    circle.setCenter(circleOptions.getCenter());
    circle.setClickable(circleOptions.isClickable());
    circle.setFillColor(circleOptions.getFillColor());
    circle.setRadius(circleOptions.getRadius());
    circle.setStrokeColor(circleOptions.getStrokeColor());
    circle.setStrokeWidth(circleOptions.getStrokeWidth());
    circle.setVisible(circleOptions.isVisible());
    circle.setZIndex(circleOptions.getZIndex());
  }

  @Nullable
  public BindableCircle retrieveBindableCircle(Circle circle) {
    for (BindableCircle bindableCircle : entities) {
      if (bindableCircle.getCircle().equals(circle)) {
        return bindableCircle;
      }
    }
    return null;
  }
}
