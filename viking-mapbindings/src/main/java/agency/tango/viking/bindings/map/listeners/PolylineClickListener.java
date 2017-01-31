package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

import agency.tango.viking.bindings.map.managers.PolylineManager;
import agency.tango.viking.bindings.map.models.BindablePolyline;

public class PolylineClickListener implements GoogleMap.OnPolylineClickListener {
  private ItemClickListener<BindablePolyline> itemClickListener;
  private final PolylineManager polylineManager;

  public PolylineClickListener(PolylineManager polylineManager) {
    this.polylineManager = polylineManager;
  }

  @Override
  public void onPolylineClick(Polyline polyline) {
    if (itemClickListener != null) {
      BindablePolyline model = getModel(polyline);
      if (model != null) {
        itemClickListener.onClick(model);
      }
    }
  }

  public void setItemClickListener(
      ItemClickListener<BindablePolyline> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  private BindablePolyline getModel(Polyline polyline) {
    return polylineManager.retrieveBindablePolyline(polyline);
  }
}
