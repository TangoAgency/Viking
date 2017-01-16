package agency.tango.viking.bindings.map;

import android.databinding.ObservableList;

import com.android.annotations.NonNull;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public abstract class MapEntityManagerBase<T, M> implements IMapEntityManager<T> {

  private final MapResolver mapResolver;
  private List<M> entities = new ArrayList<>();
  private ObservableList.OnListChangedCallback<ObservableList<T>> itemsListener;

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
            update(entities.get(i), observableList.get(i), googleMap);
          }
        });
      }

      @Override
      public void onItemRangeInserted(ObservableList<T> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i <= observableList.size() - itemCount; i++) {
            entities.add(i, create(observableList.get(i), googleMap));
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
            M entity = entities.remove(i);
            if (entity != null) {
              remove(entity, googleMap);
            }
          }
        });
      }
    };
  }

  @Override
  public void add(GoogleMap googleMap, T item) {
    entities.add(create(item, googleMap));
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
    for (M entity : entities) {
      if (compareEntities(entity, item)) {
        entities.remove(entity);
        remove(entity, googleMap);
      }
    }
  }

  @Override
  public void removeAll(GoogleMap googleMap, Collection<T> items) {
    for (M entity : entities) {
      for (T item : items) {
        if (compareEntities(entity, item)) {
          remove(entity, googleMap);
        }
      }
    }
  }

  private void addItems(Collection<T> items) {
    mapResolver.resolve(googleMap -> {
      for (M entity : entities) {
        remove(entity, googleMap);
      }
      entities.clear();

      for (T item : items) {
        add(googleMap, item);
      }
    });
  }

  abstract M create(T item, GoogleMap googleMap);

  abstract void remove(M entity, GoogleMap googleMap);

  abstract void update(M entity, T item, GoogleMap googleMap);

  abstract boolean compareEntities(M entity, T item);
}