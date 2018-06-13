package agency.tango.viking.bindings.map.models;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

/**
 * BindableCircle is a Viking class which connects CircleOptions with Circle in order to be aware
 * which circle have you created from CircleOptions.
 */
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
