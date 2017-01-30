package agency.tango.viking.bindings.map.listeners;

import agency.tango.viking.bindings.map.models.BindableMarker;

public interface MarkerDragListener<T> {
  void onMarkerDragStart(BindableMarker<T> marker);

  void onMarkerDrag(BindableMarker<T> marker);

  void onMarkerDragEnd(BindableMarker<T> marker);
}
