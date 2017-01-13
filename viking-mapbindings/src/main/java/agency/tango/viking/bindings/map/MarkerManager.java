package agency.tango.viking.bindings.map;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public class MarkerManager<T> implements IMapEntityManager<T, Marker> {
  private final MapResolver mapResolver;
  private List<Marker> markers = new ArrayList<>();
  private IMapItemAdapter<T> mapItemAdapter;
  private ObservableList.OnListChangedCallback<ObservableList<T>> itemsListener;

  public MarkerManager(MapResolver mapResolver) {
    this.mapResolver = mapResolver;
    itemsListener = new ObservableList.OnListChangedCallback<ObservableList<T>>() {
      @Override
      public void onChanged(ObservableList<T> observableList) {
        addItems(observableList);
      }

      @Override
      public void onItemRangeChanged(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        for (int i = fromIndex; i < itemCount; i++) {
          updateMarker(markers.get(i), observableList.get(i));
        }
      }

      @Override
      public void onItemRangeInserted(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i <= observableList.size() - itemCount; i++) {
            Marker marker = createMarker(googleMap, observableList.get(i));
            markers.add(i, marker);
          }
          markers.size();
        });
      }

      @Override
      public void onItemRangeMoved(ObservableList<T> observableList, int fromPosition,
          int toPosition, int itemCount) {
        moveRange(markers, fromPosition, toPosition, itemCount);
      }

      @Override
      public void onItemRangeRemoved(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap1 -> {
          for (int i = fromIndex; i < itemCount; i++) {
            Marker marker = markers.remove(i);
            if (marker != null) {
              marker.remove();
            }
          }
        });
      }
    };
  }

  @Override
  public void add(GoogleMap googleMap, T item) {
    this.markers.add(addMarkerToMap(googleMap, createMarkerOptions(item), item));
  }

  @Override
  public void addItems(GoogleMap googleMap, Collection<T> items) {
    if (items instanceof ObservableList) {
      ((ObservableList<T>) items).addOnListChangedCallback(itemsListener);
    }

    addItems(items);
  }

  @Override
  public void remove(GoogleMap googleMap, @NonNull T item) {
    for (Marker marker : markers) {
      if (item.equals(marker.getTag())) {
        markers.remove(marker);
        marker.remove();
      }
    }

  }

  @Override
  public void removeAll(GoogleMap googleMap, Collection<T> items) {
    for (Marker marker : markers) {
      for (T item : items) {
        if (item.equals(marker.getTag())) {
          markers.remove(marker);
          marker.remove();
        }
      }
    }
  }

  @NonNull
  private Marker createMarker(GoogleMap googleMap, T item) {
    Marker marker = googleMap.addMarker(createMarkerOptions(item));
    marker.setTag(item);
    return marker;
  }

  public void setItemsAdapter(IMapItemAdapter<T> mapItemAdapter) {
    this.mapItemAdapter = mapItemAdapter;
  }

  private void addItems(Collection<T> items) {
    mapResolver.resolve(googleMap -> {
      for (Marker marker : markers) {
        marker.remove();
      }
      markers.clear();

      for (T item : items) {
        add(googleMap, item);
      }
    });
  }

  private Marker addMarkerToMap(GoogleMap googleMap, MarkerOptions markerOptions, T item) {
    Marker marker = googleMap.addMarker(markerOptions);
    marker.setTag(item);

    return marker;
  }

  private MarkerOptions createMarkerOptions(T item) {
    if (mapItemAdapter == null) {
      throw new IllegalStateException("MapItemAdapter cannot be null");
    }

    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.icon(BitmapDescriptorFactory.fromResource(mapItemAdapter.icon(item)));
    markerOptions.position(mapItemAdapter.position(item));

    return markerOptions;
  }

  private void updateMarker(Marker marker, T item) {
    if (mapItemAdapter == null) {
      throw new IllegalStateException("MapItemAdapter cannot be null");
    }

    marker.setTag(item);

    marker.setIcon(BitmapDescriptorFactory.fromResource(mapItemAdapter.icon(item)));
    marker.setPosition(mapItemAdapter.position(item));
  }
}