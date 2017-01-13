package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;

public abstract class MapEntityManagerBase<T, M> implements IMapEntityManager<T> {

  abstract M create(T item, GoogleMap googleMap);

  abstract void remove(M entity, GoogleMap googleMap);

  abstract void update(M entity, T item, GoogleMap googleMap);
}