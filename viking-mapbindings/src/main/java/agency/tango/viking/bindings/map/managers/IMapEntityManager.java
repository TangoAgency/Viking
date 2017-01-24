package agency.tango.viking.bindings.map.managers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Collection;

public interface IMapEntityManager<T> {
  void add(GoogleMap googleMap, T item);

  void addItems(GoogleMap googleMap, Collection<T> items);

  interface MapResolver {
    void resolve(OnMapReadyCallback onMapReadyCallback);
  }
}
