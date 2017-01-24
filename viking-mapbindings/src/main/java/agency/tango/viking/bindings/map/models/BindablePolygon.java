package agency.tango.viking.bindings.map.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class BindablePolygon {
  private PolygonOptions polygonOptions;
  private Polygon polygon;

  public BindablePolygon(PolygonOptions polygonOptions) {
    this.polygonOptions = polygonOptions;
  }

  public PolygonOptions getPolygonOptions() {
    return polygonOptions;
  }

  @Nullable
  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }
}