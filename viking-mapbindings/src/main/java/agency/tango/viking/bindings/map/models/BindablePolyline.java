package agency.tango.viking.bindings.map.models;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * BindablePolyline is a Viking class which connects PolylineOptions with Polyline in order to be
 * aware which polyline have you created from PolylineOptions.
 */
public class BindablePolyline {
  private final PolylineOptions polylineOptions;
  private Polyline polyline;

  public BindablePolyline(PolylineOptions polylineOptions) {
    this.polylineOptions = polylineOptions;
  }

  public PolylineOptions getPolylineOptions() {
    return polylineOptions;
  }

  @Nullable
  public Polyline getPolyline() {
    return polyline;
  }

  public void setPolyline(Polyline polyline) {
    this.polyline = polyline;
  }
}
