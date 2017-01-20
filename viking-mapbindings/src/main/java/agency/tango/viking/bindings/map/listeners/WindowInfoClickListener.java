package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.model.Marker;

import agency.tango.viking.bindings.map.adapters.InfoWindowClickAdapter;
import agency.tango.viking.bindings.map.managers.MarkerManager;
import agency.tango.viking.bindings.map.models.BindableMarker;

public class WindowInfoClickListener<T> extends InfoWindowClickAdapter<BindableMarker<T>> {
  private final MarkerManager<T> markerManager;

  public WindowInfoClickListener(ItemClickListener<BindableMarker<T>> itemClickListener,
      MarkerManager<T> markerManager) {
    super(itemClickListener);
    this.markerManager = markerManager;
  }

  @Override
  public BindableMarker<T> getModel(Marker marker) {
    return markerManager.retrieveBindableMarker(marker);
  }
}