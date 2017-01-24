package agency.tango.viking.bindings.map.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

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
