package agency.tango.viking.bindings.map;

import android.databinding.ObservableList;

import com.android.annotations.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;

public class PathManager extends MapEntityManagerBase<PolylineOptions, Polyline>
    implements IMapEntityManager<PolylineOptions> {

  private final MapResolver mapResolver;
  private List<Polyline> polylines = new ArrayList<>();
  private ObservableList.OnListChangedCallback<ObservableList<PolylineOptions>> itemsListener;

  public PathManager(MapResolver mapResolver) {
    this.mapResolver = mapResolver;

    itemsListener = new ObservableList.OnListChangedCallback<ObservableList<PolylineOptions>>() {
      @Override
      public void onChanged(ObservableList<PolylineOptions> observableList) {
        mapResolver.resolve(googleMap -> addItems(googleMap, observableList));
      }

      @Override
      public void onItemRangeChanged(ObservableList<PolylineOptions> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i < itemCount; i++) {
            update(polylines.get(i), observableList.get(i), googleMap);
          }
        });
      }

      @Override
      public void onItemRangeInserted(ObservableList<PolylineOptions> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i <= observableList.size() - itemCount; i++) {
            polylines.add(i, create(observableList.get(i), googleMap));
          }
        });
      }

      @Override
      public void onItemRangeMoved(ObservableList<PolylineOptions> observableList, int fromPosition,
          int toPosition, int itemCount) {
        moveRange(polylines, fromPosition, toPosition, itemCount);
      }

      @Override
      public void onItemRangeRemoved(ObservableList<PolylineOptions> observableList, int fromIndex,
          int itemCount) {
        mapResolver.resolve(googleMap -> {
          for (int i = fromIndex; i < itemCount; i++) {
            Polyline polyline = polylines.remove(i);
            if (polyline != null) {
              polyline.remove();
            }
          }
        });
      }
    };
  }

  @Override
  public void add(GoogleMap googleMap, PolylineOptions item) {
    polylines.add(create(item, googleMap));
  }

  @Override
  public void addItems(GoogleMap googleMap, Collection<PolylineOptions> items) {
    if (items instanceof ObservableList) {
      ((ObservableList<PolylineOptions>) items).addOnListChangedCallback(itemsListener);
    }

    addItems(items);
  }

  private void addItems(Collection<PolylineOptions> items) {
    mapResolver.resolve(googleMap -> {
      for (Polyline polyline : polylines) {
        polyline.remove();
      }
      polylines.clear();

      for (PolylineOptions item : items) {
        add(googleMap, item);
      }
    });
  }

  @Override
  public void remove(GoogleMap googleMap, @NonNull PolylineOptions item) {
    for (Polyline polyline : polylines) {
      if (polyline.getPoints().equals(item.getPoints())) {
        remove(polyline, googleMap);
      }
    }
  }

  @Override
  public void removeAll(GoogleMap googleMap, Collection<PolylineOptions> items) {
    for (Polyline polyline : polylines) {
      for (PolylineOptions polylineOptions : items) {
        if (polyline.getPoints().equals(polylineOptions.getPoints())) {
          remove(polyline, googleMap);
        }
      }
    }
  }

  @Override
  Polyline create(PolylineOptions item, GoogleMap googleMap) {
    return googleMap.addPolyline(item);
  }

  @Override
  void remove(Polyline entity, GoogleMap googleMap) {
    entity.remove();
    polylines.remove(entity);
  }

  @Override
  void update(Polyline entity, PolylineOptions item, GoogleMap googleMap) {
    entity.setClickable(item.isClickable());
    entity.setPoints(item.getPoints());
    entity.setColor(item.getColor());
    entity.setGeodesic(item.isGeodesic());
    entity.setVisible(item.isVisible());
    entity.setWidth(item.getWidth());
    entity.setZIndex(item.getZIndex());
  }
}
