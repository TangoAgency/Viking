package agency.tango.viking.bindings.map.managers;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

public class ClusterItemManager<T extends ClusterItem>
    extends MapEntityManagerBase<T> {

  private ClusterManager<T> clusterManager;

  public ClusterItemManager(MapResolver mapResolver, ClusterManager<T> clusterManager) {
    super(mapResolver);
    this.clusterManager = clusterManager;
  }

  @Override
  T addToMap(T item, GoogleMap googleMap) {
    clusterManager.addItem(item);
    return item;
  }

  @Override
  void removeFromMap(T entity, GoogleMap googleMap) {
    clusterManager.removeItem(entity);
  }

  @Override
  void updateOnMap(T entity, T item, GoogleMap googleMap) {
    clusterManager.removeItem(entity);
    clusterManager.addItem(item);
  }
}
