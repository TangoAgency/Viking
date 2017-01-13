package agency.tango.viking.bindings.map;

import com.google.maps.android.clustering.ClusterItem;

public interface IClusterMapItem<T> extends ClusterItem {
  T model();
}
