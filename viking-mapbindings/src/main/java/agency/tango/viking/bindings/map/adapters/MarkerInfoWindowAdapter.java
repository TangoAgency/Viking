package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.model.Marker;

import agency.tango.viking.bindings.map.managers.MarkerManager;
import agency.tango.viking.bindings.map.models.BindableMarker;

public class MarkerInfoWindowAdapter<T>
    extends CustomInfoWindowAdapter.InfoAdapterAdapter<BindableMarker<T>> {
  private final MarkerManager<T> markerManager;

  public MarkerInfoWindowAdapter(CustomInfoWindowAdapter<BindableMarker<T>> adapter,
      MarkerManager<T> markerManager) {
    super(adapter);
    this.markerManager = markerManager;
  }

  @Override
  public BindableMarker<T> getModel(Marker marker) {
    return markerManager.retrieveBindableMarker(marker);
  }
}
