package agency.tango.viking.bindings.map.adapters;

import agency.tango.viking.bindings.map.IClusterMapItem;

public interface IClusterItemAdapter<T> {
  IClusterMapItem getItem(T item);
}
