package agency.tango.viking.bindings.map.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BindableMarker<T> {
  private final T object;
  private final MarkerOptions markerOptions;
  private Marker marker;

  public BindableMarker(T object, MarkerOptions markerOptions) {
    this.object = object;
    this.markerOptions = markerOptions;
  }

  public T getObject() {
    return object;
  }

  public MarkerOptions getMarkerOptions() {
    return markerOptions;
  }

  @Nullable
  public Marker getMarker() {
    return marker;
  }

  public void setMarker(Marker marker) {
    this.marker = marker;
  }
}
