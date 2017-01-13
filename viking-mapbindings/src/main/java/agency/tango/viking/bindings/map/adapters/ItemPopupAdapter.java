package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.GoogleMap;

public interface ItemPopupAdapter extends GoogleMap.InfoWindowAdapter {
  <T> void itemClicked(T item);
}
