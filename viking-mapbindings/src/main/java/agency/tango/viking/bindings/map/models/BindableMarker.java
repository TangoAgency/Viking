package agency.tango.viking.bindings.map.models;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * BindableMarker is a Viking class which connects MarkerOptions with Marker in order to be aware
 * which marker have you created from MarkerOptions. It also holds some model that is provided in
 * constructor.
 */
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
