package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class CompositeInfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener {
  private final List<GoogleMap.OnInfoWindowClickListener> listeners = new ArrayList<>();

  public void addOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener listener) {
    listeners.add(listener);
  }

  @Override
  public void onInfoWindowClick(Marker marker) {
    for (GoogleMap.OnInfoWindowClickListener listener : listeners) {
      listener.onInfoWindowClick(marker);
    }
  }
}
