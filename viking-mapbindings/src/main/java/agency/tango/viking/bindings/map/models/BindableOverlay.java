package agency.tango.viking.bindings.map.models;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

/**
 * BindableOverlay is a Viking class which connects GroundOverlayOptions with GroundOverlay in
 * order
 * to be aware which groundOverlay have you created from GroundOverlayOptions.
 */
public class BindableOverlay {
  private final GroundOverlayOptions groundOverlayOptions;
  private GroundOverlay groundOverlay;

  public BindableOverlay(GroundOverlayOptions groundOverlayOptions) {
    this.groundOverlayOptions = groundOverlayOptions;
  }

  public GroundOverlayOptions getGroundOverlayOptions() {
    return groundOverlayOptions;
  }

  @Nullable
  public GroundOverlay getGroundOverlay() {
    return groundOverlay;
  }

  public void setGroundOverlay(GroundOverlay groundOverlay) {
    this.groundOverlay = groundOverlay;
  }
}
