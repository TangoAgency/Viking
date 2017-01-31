package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;

import agency.tango.viking.bindings.map.managers.PolygonManager;
import agency.tango.viking.bindings.map.models.BindablePolygon;

public class PolygonClickListener implements GoogleMap.OnPolygonClickListener {
  private final PolygonManager polygonManager;
  private ItemClickListener<BindablePolygon> itemClickListener;

  public PolygonClickListener(PolygonManager polygonManager) {
    this.polygonManager = polygonManager;
  }

  @Override
  public void onPolygonClick(Polygon polygon) {
    if (itemClickListener != null) {
      BindablePolygon item = polygonManager.retrieveBindablePolygon(polygon);
      if (item != null) {
        itemClickListener.onClick(item);
      }
    }
  }

  public void setItemClickListener(
      ItemClickListener<BindablePolygon> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
}