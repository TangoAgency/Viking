package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;

public class MarkerManager<T> extends MapEntityManagerBase<T, Marker>
    implements IMapEntityManager<T> {

  private IMapItemAdapter<T> mapItemAdapter;

  public MarkerManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  public void setItemsAdapter(IMapItemAdapter<T> mapItemAdapter) {
    this.mapItemAdapter = mapItemAdapter;
  }

  @Override
  Marker create(T item, GoogleMap googleMap) {
    if (mapItemAdapter == null) {
      throw new IllegalStateException("MapItemAdapter cannot be null");
    }

    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.icon(BitmapDescriptorFactory.fromResource(mapItemAdapter.icon(item)));
    markerOptions.position(mapItemAdapter.position(item));

    Marker marker = googleMap.addMarker(markerOptions);
    marker.setTag(item);

    return marker;
  }

  @Override
  void remove(Marker entity, GoogleMap googleMap) {
    entity.remove();
  }

  @Override
  void update(Marker entity, T item, GoogleMap googleMap) {
    if (mapItemAdapter == null) {
      throw new IllegalStateException("MapItemAdapter cannot be null");
    }

    entity.setTag(item);
    entity.setIcon(BitmapDescriptorFactory.fromResource(mapItemAdapter.icon(item)));
    entity.setPosition(mapItemAdapter.position(item));
  }

  @Override
  boolean compareEntities(Marker entity, T item) {
    return item.equals(entity.getTag());
  }
}