package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;

import agency.tango.viking.bindings.map.managers.OverlayManager;
import agency.tango.viking.bindings.map.models.BindableOverlay;

public class OverlayClickListener implements GoogleMap.OnGroundOverlayClickListener {
  private final OverlayManager overlayManager;
  private ItemClickListener<BindableOverlay> itemClickListener;

  public OverlayClickListener(OverlayManager overlayManager) {
    this.overlayManager = overlayManager;
  }

  @Override
  public void onGroundOverlayClick(GroundOverlay groundOverlay) {
    if (itemClickListener != null) {
      BindableOverlay model = overlayManager.retrieveBindableOverlay(groundOverlay);
      if (model != null) {
        itemClickListener.onClick(model);
      }
    }
  }

  public ItemClickListener<BindableOverlay> getItemClickListener() {
    return itemClickListener;
  }

  public void setItemClickListener(
      ItemClickListener<BindableOverlay> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
}
