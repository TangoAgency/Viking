package agency.tango.viking.example;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class CustomClusterRenderer extends DefaultClusterRenderer<ClusterModel> {
  public CustomClusterRenderer(Context context,
      GoogleMap map,
      ClusterManager<ClusterModel> clusterManager) {
    super(context, map, clusterManager);
  }

  @Override
  protected void onBeforeClusterItemRendered(ClusterModel item, MarkerOptions markerOptions) {
    markerOptions.title(String.format("Cluster pos: %s", item.getPosition().toString()));
  }
}
