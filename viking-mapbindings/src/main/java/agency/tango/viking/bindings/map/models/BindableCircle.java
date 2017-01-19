package agency.tango.viking.bindings.map.models;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

public class BindableCircle implements MapEntity {
  private final long id;
  private CircleOptions circleOptions;
  private Circle circle;

  public BindableCircle(long id, CircleOptions circleOptions) {
    this.id = id;
    this.circleOptions = circleOptions;
  }

  @Override
  public long getId() {
    return id;
  }

  public CircleOptions getCircleOptions() {
    return circleOptions;
  }

  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }
}
