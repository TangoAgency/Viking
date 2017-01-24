package agency.tango.viking.bindings.map.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

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
