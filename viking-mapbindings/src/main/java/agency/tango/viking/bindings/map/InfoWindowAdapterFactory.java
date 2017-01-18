package agency.tango.viking.bindings.map;

import android.content.Context;

import agency.tango.viking.bindings.map.adapters.CustomInfoWindowAdapter;

public interface InfoWindowAdapterFactory<T> {
  CustomInfoWindowAdapter<T> createInfoWindowAdapter(Context context);
}
