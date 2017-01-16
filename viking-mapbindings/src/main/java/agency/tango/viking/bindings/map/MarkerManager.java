package agency.tango.viking.bindings.map;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import agency.tango.viking.bindings.map.models.BindableMarker;

public class MarkerManager<T> extends MapEntityManagerBase<BindableMarker<T>>
    implements IMapEntityManager<BindableMarker<T>> {

  public MarkerManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  BindableMarker<T> create(BindableMarker<T> item, GoogleMap googleMap) {
    item.setMarker(googleMap.addMarker(item.getMarkerOptions()));
    return item;
  }

  @Override
  void remove(BindableMarker<T> entity, GoogleMap googleMap) {
    entity.getMarker().remove();
  }

  @Override
  void update(BindableMarker<T> entity, BindableMarker<T> item, GoogleMap googleMap) {
    Marker marker = entity.getMarker();
    MarkerOptions markerOptions = item.getMarkerOptions();

    marker.setIcon(markerOptions.getIcon());
    marker.setAlpha(markerOptions.getAlpha());
    marker.setAnchor(markerOptions.getAnchorV(), markerOptions.getAnchorU());
    marker.setDraggable(markerOptions.isDraggable());
    marker.setFlat(markerOptions.isFlat());
    marker.setInfoWindowAnchor(markerOptions.getInfoWindowAnchorV(),
        markerOptions.getInfoWindowAnchorU());
    marker.setPosition(markerOptions.getPosition());
    marker.setRotation(markerOptions.getRotation());
    marker.setSnippet(markerOptions.getSnippet());
    marker.setTitle(markerOptions.getTitle());
    marker.setVisible(markerOptions.isVisible());
    marker.setZIndex(markerOptions.getZIndex());
  }

  @Nullable
  public BindableMarker<T> retrieveBindableMarker(Marker markerClicked) {
    for (BindableMarker<T> bindableMarker : entities) {
      if (bindableMarker.getMarker().equals(markerClicked)) {
        return bindableMarker;
      }
    }
    return null;
  }
}