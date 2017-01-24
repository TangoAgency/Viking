package agency.tango.viking.bindings.map.managers;

import android.content.Context;

import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class CustomClusterManager<T extends ClusterItem> {
  private ClusterManager<T> clusterManager;
  private List<OnClusterManagerReadyCallback<T>> callbacks = new ArrayList<>();

  public CustomClusterManager(Context context, IMapEntityManager.MapResolver mapResolver) {
    mapResolver.resolve(googleMap -> {
      clusterManager = new ClusterManager<>(context, googleMap);
      executeCallbacks();
    });
  }

  public void onClusterManagerReady(OnClusterManagerReadyCallback<T> callback) {
    if (clusterManager == null) {
      callbacks.add(callback);
    } else {
      callback.onClusterManagerReady(clusterManager);
    }
  }

  private void executeCallbacks() {
    for (OnClusterManagerReadyCallback<T> callback : callbacks) {
      callback.onClusterManagerReady(clusterManager);
    }
    callbacks.clear();
  }

  public interface OnClusterManagerReadyCallback<T extends ClusterItem> {

    void onClusterManagerReady(ClusterManager<T> clusterManager);
  }
}