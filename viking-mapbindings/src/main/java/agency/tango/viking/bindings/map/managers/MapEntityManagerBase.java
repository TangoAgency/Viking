package agency.tango.viking.bindings.map.managers;

import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import androidx.databinding.ObservableList;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public abstract class MapEntityManagerBase<T> implements IMapEntityManager<T> {

  private final MapResolver mapResolver;
  private WeakReferenceOnListChangedCallback<T> itemsListener;
  protected List<T> entities = new ArrayList<>();

  MapEntityManagerBase(MapResolver mapResolver) {
    this.mapResolver = mapResolver;
  }

  @Override
  public void add(GoogleMap googleMap, T item) {
    entities.add(addToMap(item, googleMap));
  }

  @Override
  public void addItems(GoogleMap googleMap, Collection<T> items) {

    if (items instanceof ObservableList) {
      itemsListener = new WeakReferenceOnListChangedCallback<T>(this);
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

  private static class WeakReferenceOnListChangedCallback<T>
      extends ObservableList.OnListChangedCallback<ObservableList<T>> {

    private final WeakReference<MapEntityManagerBase<T>> managerBaseReference;

    WeakReferenceOnListChangedCallback(MapEntityManagerBase<T> bindingRecyclerViewAdapter) {
      this.managerBaseReference = new WeakReference<>(bindingRecyclerViewAdapter);
    }

    @Override
    public void onChanged(ObservableList<T> observableList) {
      MapEntityManagerBase<T> manager = managerBaseReference.get();
      if (manager != null) {
        manager.mapResolver.resolve(googleMap -> manager.addItems(googleMap, observableList));
      }
    }

    @Override
    public void onItemRangeChanged(ObservableList<T> observableList, int fromIndex,
        int itemCount) {
      MapEntityManagerBase<T> manager = managerBaseReference.get();
      if (manager != null) {
        manager.mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i < itemCount; i++) {
            manager.updateOnMap(manager.entities.get(i), observableList.get(i), googleMap);
          }
        });
      }
    }

    @Override
    public void onItemRangeInserted(ObservableList<T> observableList, int fromIndex,
        int itemCount) {
      MapEntityManagerBase<T> manager = managerBaseReference.get();
      if (manager != null) {
        manager.mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i <= observableList.size() - itemCount; i++) {
            manager.entities.add(i, manager.addToMap(observableList.get(i), googleMap));
          }
        });
      }
    }

    @Override
    public void onItemRangeMoved(ObservableList<T> observableList, int fromPosition,
        int toPosition, int itemCount) {
      MapEntityManagerBase<T> manager = managerBaseReference.get();
      if (manager != null) {
        moveRange(manager.entities, fromPosition, toPosition, itemCount);
      }
    }

    @Override
    public void onItemRangeRemoved(ObservableList<T> observableList, int fromIndex,
        int itemCount) {
      MapEntityManagerBase<T> manager = managerBaseReference.get();
      if (manager != null) {
        manager.mapResolver.resolve(googleMap -> {

          try {
            for (int i = fromIndex; i < itemCount; i++) {
              T entity = manager.entities.remove(i);
              if (entity != null) {
                manager.removeFromMap(entity, googleMap);
              }
            }
          }
          catch (Exception e) {
            Log.e("Viking","Something went wrong during removing items");
          }
        });
      }
    }
  }
}