package agency.tango.viking.bindings.map.listeners;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class CompositeOnCameraIdleListener implements GoogleMap.OnCameraIdleListener {
  private final List<GoogleMap.OnCameraIdleListener> listeners = new ArrayList<>();

  public void addOnCameraIdleListener(GoogleMap.OnCameraIdleListener listener) {
    listeners.add(listener);
  }

  @Override
  public void onCameraIdle() {
    for (GoogleMap.OnCameraIdleListener listener : listeners) {
      listener.onCameraIdle();
    }
  }
}
