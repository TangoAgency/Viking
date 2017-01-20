package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import agency.tango.viking.bindings.map.listeners.ItemClickListener;

public abstract class InfoWindowClickAdapter<T> implements GoogleMap.OnInfoWindowClickListener {
  private final ItemClickListener<T> itemClickListener;

  public InfoWindowClickAdapter(ItemClickListener<T> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public abstract T getModel(Marker marker);

  @Override
  public void onInfoWindowClick(Marker marker) {
    T model = getModel(marker);
    if (model != null) {
      itemClickListener.onClick(model);
    }
  }
}
