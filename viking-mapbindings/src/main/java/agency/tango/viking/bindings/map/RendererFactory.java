package agency.tango.viking.bindings.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public interface RendererFactory<T extends ClusterItem> {
  ClusterRenderer<T> createRenderer(Context context, GoogleMap googleMap,
      ClusterManager<T> clusterManager);

  class DefaultRendererFactory<T extends ClusterItem> implements RendererFactory<T> {
    @Override
    public ClusterRenderer<T> createRenderer(Context context, GoogleMap googleMap,
        ClusterManager<T> clusterManager) {
      return new DefaultClusterRenderer<>(context, googleMap, clusterManager);
    }
  }
}