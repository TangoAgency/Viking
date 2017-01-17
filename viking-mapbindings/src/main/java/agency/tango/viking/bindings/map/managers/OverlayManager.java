package agency.tango.viking.bindings.map.managers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

import agency.tango.viking.bindings.map.models.BindableOverlay;

public class OverlayManager extends MapEntityManagerBase<BindableOverlay>
    implements IMapEntityManager<BindableOverlay> {

  public OverlayManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  BindableOverlay addToMap(BindableOverlay item, GoogleMap googleMap) {
    item.setGroundOverlay(googleMap.addGroundOverlay(item.getGroundOverlayOptions()));
    return item;
  }

  @Override
  void removeFromMap(BindableOverlay entity, GoogleMap googleMap) {
    entity.getGroundOverlay().remove();
  }

  @Override
  void updateOnMap(BindableOverlay entity, BindableOverlay item, GoogleMap googleMap) {
    GroundOverlay groundOverlay = entity.getGroundOverlay();
    GroundOverlayOptions groundOverlayOptions = item.getGroundOverlayOptions();

    if (groundOverlayOptions.getBounds() != null) {
      groundOverlay.setPositionFromBounds(groundOverlayOptions.getBounds());
    } else {
      groundOverlay.setPosition(groundOverlayOptions.getLocation());
    }
    groundOverlay.setImage(groundOverlayOptions.getImage());
    groundOverlay.setBearing(groundOverlayOptions.getBearing());
    groundOverlay.setZIndex(groundOverlayOptions.getZIndex());
    groundOverlay.setTransparency(groundOverlayOptions.getTransparency());
    groundOverlay.setVisible(groundOverlayOptions.isVisible());
    groundOverlay.setClickable(groundOverlayOptions.isClickable());
  }
}