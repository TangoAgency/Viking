package agency.tango.viking.example;

import com.google.android.gms.maps.model.LatLng;

public class ExampleModel {
  private final LatLng latLng;

  public ExampleModel(LatLng latLng) {
    this.latLng = latLng;
  }

  public LatLng getLatLng() {
    return latLng;
  }
}
