package agency.tango.viking.bindings.map.models;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class BindablePolyline implements MapEntity {
  private final long id;
  private final PolylineOptions polylineOptions;
  private Polyline polyline;

  public BindablePolyline(long id, PolylineOptions polylineOptions) {
    this.id = id;
    this.polylineOptions = polylineOptions;
  }

  @Override
  public long getId() {
    return id;
  }

  public PolylineOptions getPolylineOptions() {
    return polylineOptions;
  }

  public Polyline getPolyline() {
    return polyline;
  }

  public void setPolyline(Polyline polyline) {
    this.polyline = polyline;
  }
}
