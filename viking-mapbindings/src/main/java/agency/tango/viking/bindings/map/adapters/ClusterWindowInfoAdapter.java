package agency.tango.viking.bindings.map.adapters;

import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ClusterWindowInfoAdapter<T extends ClusterItem> extends
    CustomInfoWindowAdapter.InfoAdapterAdapter<Cluster<T>> {

  private final ClusterManager<T> clusterManager;

  public ClusterWindowInfoAdapter(CustomInfoWindowAdapter<Cluster<T>> adapter,
      ClusterManager<T> clusterManager) {
    super(adapter);
    this.clusterManager = clusterManager;
  }

  @Override
  public Cluster<T> getModel(Marker marker) {
    return ((DefaultClusterRenderer<T>) clusterManager.getRenderer()).getCluster(marker);
  }
}
