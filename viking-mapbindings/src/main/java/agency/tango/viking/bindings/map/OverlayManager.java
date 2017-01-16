package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class OverlayManager extends MapEntityManagerBase<GroundOverlayOptions, GroundOverlay>
    implements IMapEntityManager<GroundOverlayOptions> {

  OverlayManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  GroundOverlay create(GroundOverlayOptions item, GoogleMap googleMap) {
    return googleMap.addGroundOverlay(item);
  }

  @Override
  void remove(GroundOverlay entity, GoogleMap googleMap) {
    entity.remove();
  }

  @Override
  void update(GroundOverlay entity, GroundOverlayOptions item, GoogleMap googleMap) {
    if (item.getBounds() != null) {
      entity.setPositionFromBounds(item.getBounds());
    } else {
      entity.setPosition(item.getLocation());
    }
    entity.setImage(item.getImage());
    entity.setBearing(item.getBearing());
    entity.setZIndex(item.getZIndex());
    entity.setTransparency(item.getTransparency());
    entity.setVisible(item.isVisible());
    entity.setClickable(item.isClickable());
  }

  @Override
  boolean compareEntities(GroundOverlay entity, GroundOverlayOptions item) {
    if (entity.getBounds() != null && item.getBounds() != null) {
      return entity.getBounds().equals(item.getBounds());
    }

    return entity.getPosition().equals(item.getLocation());
  }
}