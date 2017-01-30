package agency.tango.viking.bindings.map.managers;

import android.databinding.ObservableList;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public abstract class MapEntityManagerBase<T> implements IMapEntityManager<T> {

  private final MapResolver mapResolver;
  private ObservableList.OnListChangedCallback<ObservableList<T>> itemsListener;
  protected List<T> entities = new ArrayList<>();

  MapEntityManagerBase(MapResolver mapResolver) {
    this.mapResolver = mapResolver;

    itemsListener = new ObservableList.OnListChangedCallback<ObservableList<T>>() {
      @Override
      public void onChanged(ObservableList<T> observableList) {
        mapResolver.resolve(googleMap -> addItems(googleMap, observableList));
      }

      @Override
      public void onItemRangeChanged(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i < itemCount; i++) {
            updateOnMap(entities.get(i), observableList.get(i), googleMap);
          }
        });
      }

      @Override
      public void onItemRangeInserted(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i <= observableList.size() - itemCount; i++) {
            entities.add(i, addToMap(observableList.get(i), googleMap));
          }
        });
      }

      @Override
      public void onItemRangeMoved(ObservableList<T> observableList, int fromPosition,
          int toPosition, int itemCount) {
        moveRange(entities, fromPosition, toPosition, itemCount);
      }

      @Override
      public void onItemRangeRemoved(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i < itemCount; i++) {
            T entity = entities.remove(i);
            if (entity != null) {
              removeFromMap(entity, googleMap);
            }
          }
        });
      }
    };
  }

  @Override
  public void add(GoogleMap googleMap, T item) {
    entities.add(addToMap(item, googleMap));
  }

  @Override
  public void addItems(GoogleMap googleMap, Collection<T> items) {
    if (items instanceof ObservableList) {
      ((ObservableList<T>) items).addOnListChangedCallback(itemsListener);
    }

    addItems(items);
  }

  private void addItems(Collection<T> items) {
    mapResolver.resolve(googleMap -> {
      for (T entity : entities) {
        removeFromMap(entity, googleMap);
      }
      entities.clear();

      for (T item : items) {
        add(googleMap, item);
      }
    });
  }

  protected abstract T addToMap(T item, GoogleMap googleMap);

  protected abstract void removeFromMap(T entity, GoogleMap googleMap);

  protected abstract void updateOnMap(T entity, T item, GoogleMap googleMap);
}