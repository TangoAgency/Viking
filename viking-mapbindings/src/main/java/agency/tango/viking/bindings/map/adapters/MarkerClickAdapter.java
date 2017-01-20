package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import agency.tango.viking.bindings.map.listeners.ItemClickListener;

public abstract class MarkerClickAdapter<T> implements GoogleMap.OnMarkerClickListener {
  private final ItemClickListener<T> itemClickListener;

  public MarkerClickAdapter(ItemClickListener<T> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public abstract T getModel(Marker marker);

  @Override
  public boolean onMarkerClick(Marker marker) {
    T model = getModel(marker);
    if (model != null) {
      itemClickListener.onClick(model);
      return true;
    }
    return false;
  }
}
