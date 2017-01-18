package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ClusterItemWindowInfoAdapter<T extends ClusterItem>
    extends CustomInfoWindowAdapter.InfoAdapterAdapter<T> {
  private final ClusterManager<T> clusterManager;

  public ClusterItemWindowInfoAdapter(CustomInfoWindowAdapter<T> adapter, ClusterManager<T> clusterManager) {
    super(adapter);
    this.clusterManager = clusterManager;
  }

  @Override
  public T getModel(Marker marker) {
    return ((DefaultClusterRenderer<T>) clusterManager.getRenderer()).getClusterItem(marker);
  }
}
