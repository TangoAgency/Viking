package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import agency.tango.viking.bindings.map.listeners.OnMarkerClickListener;

public abstract class MarkerClickAdapter<T> implements GoogleMap.OnMarkerClickListener {
  private final OnMarkerClickListener<T> itemClickListener;

  public MarkerClickAdapter(OnMarkerClickListener<T> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public abstract T getModel(Marker marker);

  @Override
  public boolean onMarkerClick(Marker marker) {
    T model = getModel(marker);
    return model != null && itemClickListener.onClick(model);
  }
}
