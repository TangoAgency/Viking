package agency.tango.viking.bindings.map.models;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * BindablePolygon is a Viking class which connects PolygonOptions with Polygon in order to be
 * aware which polygon have you created from PolygonOptions.
 */
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