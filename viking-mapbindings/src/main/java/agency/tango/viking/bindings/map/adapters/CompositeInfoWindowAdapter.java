package agency.tango.viking.bindings.map.adapters;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class CompositeInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
  private List<GoogleMap.InfoWindowAdapter> infoWindowAdapters = new ArrayList<>();


  public void addInfoWindowAdapter(GoogleMap.InfoWindowAdapter adapter) {
    infoWindowAdapters.add(adapter);
  }

  @Override
  public View getInfoWindow(Marker marker) {
    for (GoogleMap.InfoWindowAdapter infoWindowAdapter : infoWindowAdapters) {
      View result = infoWindowAdapter.getInfoWindow(marker);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  @Override
  public View getInfoContents(Marker marker) {
    for (GoogleMap.InfoWindowAdapter infoWindowAdapter : infoWindowAdapters) {
      View result = infoWindowAdapter.getInfoContents(marker);
      if (result != null) {
        return result;
      }
    }
    return null;
  }
}
