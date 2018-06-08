package agency.tango.viking.example;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterModel implements ClusterItem {
  private final LatLng position;

  public ClusterModel(LatLng position) {
    this.position = position;
  }

  @Override
  public LatLng getPosition() {
    return position;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public String getSnippet() {
    return null;
  }
}
