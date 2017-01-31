package agency.tango.viking.bindings.map.adapters;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public interface CustomInfoWindowAdapter<T> {
  View getInfoWindow(T bindableMarker);

  View getInfoContents(T bindableMarker);

  abstract class InfoAdapterAdapter<T> implements GoogleMap.InfoWindowAdapter {

    private final CustomInfoWindowAdapter<T> adapter;

    InfoAdapterAdapter(CustomInfoWindowAdapter<T> adapter) {
      this.adapter = adapter;
    }

    public abstract T getModel(Marker marker);

    @Override
    public View getInfoWindow(Marker marker) {
      T model = getModel(marker);
      if (model != null) {
        return adapter.getInfoWindow(model);
      }
      return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
      T model = getModel(marker);
      if (model != null) {
        return adapter.getInfoContents(model);
      }
      return null;
    }
  }
}
