package agency.tango.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.Algorithm;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.bindings.map.InfoWindowAdapterFactory;
import agency.tango.viking.bindings.map.RendererFactory;

@SuppressWarnings({ "unused" })
public class ClusterBindings {
  private ClusterBindings() {
  }

  @BindingAdapter("gmv_clusterItems")
  public static <T> void clusterItems(GoogleMapView googleMapView,
      Collection<T> items) {
    if (items == null) {
      return;
    }

    googleMapView.clusterItems(items);
  }

  @BindingAdapter("gmv_rendererFactory")
  public static <T extends ClusterItem> void rendererFactory(GoogleMapView googleMapView,
      RendererFactory<T> rendererFactory) {
    googleMapView.setRendererFactory(rendererFactory);
  }

  @BindingAdapter("gmv_algorithm")
  public static <T extends ClusterItem> void algorithm(GoogleMapView googleMapView,
      Algorithm<T> algorithm) {
    googleMapView.setAlgorithm(algorithm);
  }

  @BindingAdapter("gmv_clusterItemInfoWindowAdapter")
  public static <T extends ClusterItem> void clusterItemInfoWindowAdapter(
      GoogleMapView googleMapView,
      InfoWindowAdapterFactory<T> infoWindowAdapterFactory) {
    googleMapView.setClusterItemInfoWindowAdapter(infoWindowAdapterFactory);
  }

  @BindingAdapter("gmv_clusterInfoWindowAdapter")
  public static <T extends Cluster> void clusterInfoWindowAdapter(GoogleMapView googleMapView,
      InfoWindowAdapterFactory<T> infoWindowAdapterFactory) {
    googleMapView.setClusterInfoWindowAdapter(infoWindowAdapterFactory);
  }

  @BindingAdapter("gmv_clusterClickListener")
  public static <T extends ClusterItem> void clusterClickListener(GoogleMapView googleMapView,
      ClusterManager.OnClusterClickListener<T> onClusterClickListener) {
    googleMapView.setOnClusterClickListener(onClusterClickListener);
  }

  @BindingAdapter("gmv_clusterItemClickListener")
  public static <T extends ClusterItem> void clusterItemClickListener(
      GoogleMapView googleMapView,
      ClusterManager.OnClusterItemClickListener<T> onClusterItemClickListener) {
    googleMapView.setOnClusterItemClickListener(onClusterItemClickListener);
  }

  @BindingAdapter("gmv_clusterInfoWindowClickListener")
  public static <T extends ClusterItem> void clusterInfoWindowClickListener(
      GoogleMapView googleMapView,
      ClusterManager.OnClusterInfoWindowClickListener<T> onClusterInfoWindowClickListener) {
    googleMapView.setOnClusterInfoWindowClickListener(onClusterInfoWindowClickListener);
  }

  @BindingAdapter("gmv_clusterItemInfoWindowClickListener")
  public static <T extends ClusterItem> void onClusterItemInfoWindowClickListener(
      GoogleMapView googleMapView,
      ClusterManager.OnClusterItemInfoWindowClickListener<T> onClusterItemInfoWindowClickListener) {
    googleMapView.setOnClusterItemInfoWindowClickListener(onClusterItemInfoWindowClickListener);
  }
}