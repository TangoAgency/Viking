package agency.tango.viking.bindings.map.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

public class BindableCircle {

  private CircleOptions circleOptions;
  private Circle circle;

  public BindableCircle(CircleOptions circleOptions) {
    this.circleOptions = circleOptions;
  }

  public CircleOptions getCircleOptions() {
    return circleOptions;
  }

  @Nullable
  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }
}
