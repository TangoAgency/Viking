package agency.tango.viking.bindings.map.models;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class BindablePolygon implements MapEntity {
  private PolygonOptions polygonOptions;
  private Polygon polygon;
  private final long id;

  public BindablePolygon(long id, PolygonOptions polygonOptions) {
    this.id = id;
    this.polygonOptions = polygonOptions;
  }

  @Override
  public long getId() {
    return id;
  }

  public PolygonOptions getPolygonOptions() {
    return polygonOptions;
  }

  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }
}