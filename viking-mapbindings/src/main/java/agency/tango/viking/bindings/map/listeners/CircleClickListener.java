package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;

import agency.tango.viking.bindings.map.managers.CircleManager;
import agency.tango.viking.bindings.map.models.BindableCircle;

public class CircleClickListener implements GoogleMap.OnCircleClickListener {
  private ItemClickListener<BindableCircle> itemClickListener;
  private final CircleManager circleManager;

  public CircleClickListener(CircleManager circleManager) {
    this.circleManager = circleManager;
  }

  @Override
  public void onCircleClick(Circle circle) {
    if (itemClickListener != null) {
      BindableCircle item = circleManager.retrieveBindableCircle(circle);
      if (item != null) {
        itemClickListener.onClick(item);
      }
    }
  }

  public void setItemClickListener(
      ItemClickListener<BindableCircle> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
}
