package agency.tango.viking.example;

import com.google.android.gms.maps.model.PolygonOptions;

import agency.tango.viking.bindings.map.models.BindablePolygon;

public class ExamplePolygon extends BindablePolygon {
  private final ExampleModel model;

  public ExamplePolygon(PolygonOptions polygonOptions, ExampleModel model) {
    super(polygonOptions);
    this.model = model;
  }
}
