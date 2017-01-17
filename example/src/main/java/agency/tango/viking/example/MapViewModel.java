package agency.tango.viking.example;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import agency.tango.viking.bindings.map.clickHandlers.ItemClickListener;
import agency.tango.viking.bindings.map.models.BindableMarker;
import agency.tango.viking.bindings.map.models.BindableOverlay;
import agency.tango.viking.bindings.map.models.BindablePolyline;
import agency.tango.viking.mvvm.ViewModel;
import agency.tango.viking.rx.util.SchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MapViewModel extends ViewModel {
  public static final float DEFAULT_ZOOM = 15;

  private final ObservableList<BindableMarker<ExampleModel>> models = new ObservableArrayList<>();
  private final ObservableList<BindablePolyline> polylines = new ObservableArrayList<>();
  private final ObservableList<BindableOverlay> overlays = new ObservableArrayList<>();
  private final ObservableList<ClusterModel> clusterItems = new ObservableArrayList<>();

  private HeatmapTileProvider heatmapTileProvider;

  private LatLng latLng;
  private float zoom = DEFAULT_ZOOM;

  @Inject
  public MapViewModel() {
  }

  @Override
  public void start() {
    super.start();

    clusterItems.add(new ClusterModel(0, new LatLng(1.101110, 0.000000)));
    clusterItems.add(new ClusterModel(1, new LatLng(2.202222, 0.000002)));
    clusterItems.add(new ClusterModel(2, new LatLng(3.303334, 0.000004)));
    clusterItems.add(new ClusterModel(3, new LatLng(4.404446, 0.000006)));
    clusterItems.add(new ClusterModel(4, new LatLng(5.505558, 0.000008)));
    clusterItems.add(new ClusterModel(5, new LatLng(6.606660, 0.000010)));

    models.add(new BindableMarker<>(
        new ExampleModel(new LatLng(0, 0)),
        0,
        new MarkerOptions()
            .title("marker")
            .position(new LatLng(0, 0))));

    polylines.add(new BindablePolyline(0,
        new PolylineOptions().add(new LatLng(0, 0)).add(new LatLng(50, 50))));

    overlays.add(new BindableOverlay(0,
        new GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.amu_bubble_mask))
            .positionFromBounds(new LatLngBounds(new LatLng(0, 0), new LatLng(5, 5)))));

    heatmapTileProvider = new HeatmapTileProvider.Builder()
        .data(Arrays.asList(new LatLng(0, 0),
            new LatLng(1, 1),
            new LatLng(2, 2)))
        .radius(50)
        .build();

    Observable.just(1).delay(4, TimeUnit.SECONDS, SchedulerProvider.getInstance()
        .computation())
        .observeOn(SchedulerProvider.getInstance().ui())
        .subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {

            polylines.set(0, new BindablePolyline(0,
                new PolylineOptions().add(new LatLng(25, 14)).add(new LatLng(25, 50))));

            overlays.set(0, new BindableOverlay(0,
                new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.amu_bubble_mask))
                    .positionFromBounds(new LatLngBounds(new LatLng(5, 10), new LatLng(10, 15)))));

            heatmapTileProvider = new HeatmapTileProvider.Builder()
                .data(Arrays.asList(new LatLng(18, 18),
                    new LatLng(19, 19),
                    new LatLng(20, 20)))
                .radius(50)
                .build();
            notifyPropertyChanged(BR.heatMap);
          }
        });
  }

  @Bindable
  public Collection<ClusterModel> getClusterItems() {return clusterItems;}

  @Bindable
  public Collection<BindableMarker<ExampleModel>> getModels() {
    return models;
  }

  @Bindable
  public Collection<BindablePolyline> getPolylines() {
    return polylines;
  }

  @Bindable
  public Collection<BindableOverlay> getOverlays() {return overlays;}

  @Bindable
  public HeatmapTileProvider getHeatMap() {

    return heatmapTileProvider;
  }

  @Bindable
  public LatLng getLatLng() {
    return this.latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
    this.notifyPropertyChanged(BR.latLng);
  }

  @Bindable
  public float getZoom() {
    return zoom;
  }

  public void setZoom(float zoom) {
    this.zoom = zoom;
    notifyPropertyChanged(BR.zoom);
  }

  @Bindable
  public ItemClickListener<BindableMarker<ExampleModel>> getMarkerClickListener() {
    return new ItemClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public void onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().setPosition(new LatLng(20, 20));
      }
    };
  }

  @Bindable
  public ItemClickListener<BindableMarker<ExampleModel>> getInfoWindowClickListener() {
    return new ItemClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public void onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().setPosition(new LatLng(-20, -20));
        item.getMarker().hideInfoWindow();
      }
    };
  }

  @Bindable
  public GoogleMap.InfoWindowAdapter getInfoWindowAdapter() {
    return new GoogleMap.InfoWindowAdapter() {
      @Override
      public View getInfoWindow(Marker marker) {
        return null;
      }

      @Override
      public View getInfoContents(Marker marker) {
        return null;
      }
    };
  }

  @Bindable
  public ClusterManager.OnClusterClickListener getClusterClickListener() {
    return new ClusterManager.OnClusterClickListener() {
      @Override
      public boolean onClusterClick(Cluster cluster) {
        setLatLng(cluster.getPosition());
        return false;
      }
    };
  }
}
