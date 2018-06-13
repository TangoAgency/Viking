package agency.tango.viking.bindings.map.managers;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

import agency.tango.viking.bindings.map.models.BindableCircle;

public class CircleManager<T extends BindableCircle> extends MapEntityManagerBase<T> {
  public CircleManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  protected T addToMap(T item, GoogleMap googleMap) {
    item.setCircle(googleMap.addCircle(item.getCircleOptions()));
    return item;
  }

  @Override
  protected void removeFromMap(T entity, GoogleMap googleMap) {
    entity.getCircle().remove();
  }

  @Override
  protected void updateOnMap(T entity, T item, GoogleMap googleMap) {
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
  public T retrieveBindableCircle(Circle circle) {
    for (T bindableCircle : entities) {
      if (bindableCircle.getCircle().equals(circle)) {
        return bindableCircle;
      }
    }
    return null;
  }
}
