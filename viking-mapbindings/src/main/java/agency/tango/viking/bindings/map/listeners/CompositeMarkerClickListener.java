package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class CompositeMarkerClickListener implements GoogleMap.OnMarkerClickListener {
  private final List<GoogleMap.OnMarkerClickListener> listeners = new ArrayList<>();

  public void addOnMarkerClickListener(GoogleMap.OnMarkerClickListener onMarkerClickListener) {
    listeners.add(onMarkerClickListener);
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    for (GoogleMap.OnMarkerClickListener listener : listeners) {
      boolean consumed = listener.onMarkerClick(marker);
      if (consumed) {
        return true;
      }
    }
    return false;
  }
}
