package agency.tango.viking.example;

import com.google.android.gms.maps.model.LatLng;

import agency.tango.viking.bindings.map.ClusterMapItem;

public class ClusterModel implements ClusterMapItem {

  private final long id;
  private final LatLng position;

  public ClusterModel(long id, LatLng position) {
    this.id = id;
    this.position = position;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public LatLng getPosition() {
    return position;
  }
}
