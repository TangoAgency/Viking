package agency.tango.viking.bindings.map.models;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class BindableOverlay implements MapEntity {
  private final long id;
  private final GroundOverlayOptions groundOverlayOptions;
  private GroundOverlay groundOverlay;

  public BindableOverlay(long id, GroundOverlayOptions groundOverlayOptions) {
    this.id = id;
    this.groundOverlayOptions = groundOverlayOptions;
  }

  @Override
  public long getId() {
    return id;
  }

  public GroundOverlayOptions getGroundOverlayOptions() {
    return groundOverlayOptions;
  }

  public GroundOverlay getGroundOverlay() {
    return groundOverlay;
  }

  public void setGroundOverlay(GroundOverlay groundOverlay) {
    this.groundOverlay = groundOverlay;
  }
}
