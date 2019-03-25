package agency.tango.viking.example;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.StaticCluster;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import java.util.Arrays;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;

import agency.tango.viking.annotations.ProvidesViewModel;
import agency.tango.viking.bindings.map.InfoWindowAdapterFactory;
import agency.tango.viking.bindings.map.RendererFactory;
import agency.tango.viking.bindings.map.adapters.CustomInfoWindowAdapter;
import agency.tango.viking.bindings.map.listeners.ItemClickListener;
import agency.tango.viking.bindings.map.listeners.OnMarkerClickListener;
import agency.tango.viking.bindings.map.models.BindableCircle;
import agency.tango.viking.bindings.map.models.BindableMarker;
import agency.tango.viking.bindings.map.models.BindableOverlay;
import agency.tango.viking.bindings.map.models.BindablePolygon;
import agency.tango.viking.bindings.map.models.BindablePolyline;
import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.mvvm.StartupAction;
import agency.tango.viking.mvvm.ViewModel;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

@ProvidesViewModel
public class MapViewModel extends ViewModel {
  private static final float DEFAULT_ZOOM = 2;

  private final ObservableList<BindableMarker<ExampleModel>> models = new ObservableArrayList<>();
  private final ObservableList<BindablePolyline> polylines = new ObservableArrayList<>();
  private final ObservableList<BindableOverlay> overlays = new ObservableArrayList<>();
  private final ObservableList<BindableCircle> circles = new ObservableArrayList<>();
  private final ObservableList<BindablePolygon> polygons = new ObservableArrayList<>();
  private final ObservableList<ClusterModel> clusterItems = new ObservableArrayList<>();

  private HeatmapTileProvider heatmapTileProvider;

  private LatLng latLng;
  private LatLngBounds bounds;

  private float zoom = DEFAULT_ZOOM;

  MutableLiveData<NavigatorOperation> test = new SingleLiveEvent<>();

  @Inject
  MapViewModel(Context context, @Named("test_string") String test, @Named("second_test_string") String secondTest) {
    Log.d("MapViewModel", this.toString() + " " + context.toString());
    Log.d("MapViewModel", test);
    runOnStartup(new StartupAction() {
      @Override
      public void execute() {
        clusterItems.add(new ClusterModel(new LatLng(0, 11.101110)));
        clusterItems.add(new ClusterModel(new LatLng(0, 12.202222)));
        clusterItems.add(new ClusterModel(new LatLng(0, 13.303334)));
        clusterItems.add(new ClusterModel(new LatLng(3, 11.101110)));
        clusterItems.add(new ClusterModel(new LatLng(3, 12.202222)));
        clusterItems.add(new ClusterModel(new LatLng(3, 13.303334)));

        circles.add(new BindableCircle(new CircleOptions()
            .radius(100000)
            .clickable(true)
            .center(new LatLng(0, 5))));

        ExampleModel exampleModel = new ExampleModel("Hello", "World");
        models.add(new BindableMarker<>(
            exampleModel,
            new MarkerOptions()
                .title("marker")
                .position(new LatLng(0, -10))));

        polylines.add(new BindablePolyline(
            new PolylineOptions()
                .clickable(true)
                .add(new LatLng(0, 25))
                .add(new LatLng(0, 30))));

        polygons.add(new BindablePolygon(new PolygonOptions()
            .clickable(true)
            .strokeColor(Color.rgb(255, 0, 0))
            .add(new LatLng(0, 0))
            .add(new LatLng(0, 1))
            .add(new LatLng(1, 1))
            .add(new LatLng(1, 0))));

        overlays.add(new BindableOverlay(
            new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.heart))
                .positionFromBounds(new LatLngBounds(new LatLng(0, -4), new LatLng(1, -3)))));

        heatmapTileProvider = new HeatmapTileProvider.Builder()
            .data(Arrays.asList(new LatLng(0, 20),
                new LatLng(0, 21),
                new LatLng(0, 22)))
            .radius(50)
            .build();

        setZoom(10f);
        setLatLng(new LatLng(52.52, 13.40));
      }
    });
  }

  @Override
  public void start() {
    super.start();
  }

  @Bindable
  public Collection<ClusterModel> getClusterItems() {
    return clusterItems;
  }

  @Bindable
  public Collection<BindableMarker<ExampleModel>> getModels() {
    return models;
  }

  @Bindable
  public Collection<BindablePolyline> getPolylines() {
    return polylines;
  }

  @Bindable
  public Collection<BindableOverlay> getOverlays() {
    return overlays;
  }

  @Bindable
  public Collection<BindableCircle> getCircles() {
    return circles;
  }

  @Bindable
  public Collection<BindablePolygon> getPolygons() {
    return polygons;
  }

  @Bindable
  public HeatmapTileProvider getHeatMap() {
    return heatmapTileProvider;
  }

  @Bindable
  public LatLngBounds getBounds() {
    return bounds;
  }

  public void setBounds(LatLngBounds bounds) {
    this.bounds = bounds;
    notifyPropertyChanged(BR.bounds);
  }

  @Bindable
  public String getLocation() {
    if (latLng != null) {
      return latLng.toString();
    }
    return "";
  }

  @Bindable
  public LatLng getLatLng() {
    return this.latLng;
  }

  public void setLatLng(LatLng latLng) {
    Log.d("A", "LatLng: " + latLng);
    this.latLng = latLng;
    this.notifyPropertyChanged(BR.latLng);
    this.notifyPropertyChanged(BR.location);
  }

  @Bindable
  public float getZoom() {
    return zoom;
  }

  public void setZoom(float zoom) {
    Log.d("A", "Zoom: " + zoom);

    if (this.zoom != zoom) {
      notifyPropertyChanged(BR.zoom);
      this.zoom = zoom;
    }
  }

  @Bindable
  public OnMarkerClickListener<BindableMarker<ExampleModel>> getMarkerClickListener() {
    return new OnMarkerClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public boolean onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().showInfoWindow();

        test.postValue(new NavigatorOperation() {
          @Override
          public void navigate(Navigator navigator) {
            navigator.openMainActivity();
          }
        });

        return true;
      }
    };
  }

  @Bindable
  public ItemClickListener<BindableMarker<ExampleModel>> getInfoWindowClickListener() {
    return new ItemClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public void onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().hideInfoWindow();
      }
    };
  }

  @Bindable
  public ItemClickListener<BindablePolyline> getPolylineClickListener() {
    return new ItemClickListener<BindablePolyline>() {
      @Override
      public void onClick(BindablePolyline item) {
        item.getPolyline().setWidth(50f);
      }
    };
  }

  @Bindable
  public ItemClickListener<BindableOverlay> getOverlayClickListener() {
    return new ItemClickListener<BindableOverlay>() {
      @Override
      public void onClick(BindableOverlay item) {
        item.getGroundOverlay().setPosition(new LatLng(0, 0));
      }
    };
  }

  @Bindable
  public ItemClickListener<BindablePolygon> getPolygonClickListener() {
    return new ItemClickListener<BindablePolygon>() {
      @Override
      public void onClick(BindablePolygon item) {
        item.getPolygon().setFillColor(R.color.colorAccent);
      }
    };
  }

  @Bindable
  public ItemClickListener<BindableCircle> getCircleClickListener() {
    return new ItemClickListener<BindableCircle>() {
      @Override
      public void onClick(BindableCircle item) {
        item.getCircle().setFillColor(R.color.colorAccent);
      }
    };
  }

  @Bindable
  public InfoWindowAdapterFactory<BindableMarker<ExampleModel>> getInfoWindowAdapter() {
    return new InfoWindowAdapterFactory<BindableMarker<ExampleModel>>() {
      @Override
      public CustomInfoWindowAdapter<BindableMarker<ExampleModel>> createInfoWindowAdapter(
          final Context context) {
        return new CustomInfoWindowAdapter<BindableMarker<ExampleModel>>() {
          @Override
          public View getInfoWindow(BindableMarker<ExampleModel> bindableMarker) {
            return null;
          }

          @Override
          public View getInfoContents(BindableMarker<ExampleModel> bindableMarker) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView description = (TextView) view.findViewById(R.id.tv_description);

            title.setText(bindableMarker.getObject().getTitle());
            description.setText(bindableMarker.getObject().getDescription());
            return view;
          }
        };
      }
    };
  }

  @Bindable
  public InfoWindowAdapterFactory<ClusterItem> getClusterItemInfoWindowAdapter() {
    return new InfoWindowAdapterFactory<ClusterItem>() {
      @Override
      public CustomInfoWindowAdapter<ClusterItem> createInfoWindowAdapter(
          final Context context) {
        return new CustomInfoWindowAdapter<ClusterItem>() {
          @Override
          public View getInfoWindow(ClusterItem clusterItem) {
            return null;
          }

          @Override
          public View getInfoContents(ClusterItem clusterItem) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

            TextView title = (TextView) view.findViewById(R.id.tv_title);

            title.setText(String.format("LatLng: %s", clusterItem.getPosition().toString()));
            return view;
          }
        };
      }
    };
  }

  @Bindable
  public InfoWindowAdapterFactory<StaticCluster> getClusterInfoWindowAdapter() {
    return new InfoWindowAdapterFactory<StaticCluster>() {
      @Override
      public CustomInfoWindowAdapter<StaticCluster> createInfoWindowAdapter(final Context context) {
        return new CustomInfoWindowAdapter<StaticCluster>() {
          @Override
          public View getInfoWindow(StaticCluster cluster) {
            return null;
          }

          @Override
          public View getInfoContents(StaticCluster cluster) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView description = (TextView) view.findViewById(R.id.tv_description);

            title.setText(String.format("SIZE: %d", cluster.getItems().size()));
            description.setText(String.format("LatLng: %f %f", cluster.getPosition().latitude,
                cluster.getPosition().longitude));
            return view;
          }
        };
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

  @Bindable
  public RendererFactory<ClusterModel> getRendererFactory() {
    return new RendererFactory<ClusterModel>() {
      @Override
      public ClusterRenderer<ClusterModel> createRenderer(Context context, GoogleMap googleMap,
          ClusterManager<ClusterModel> clusterManager) {
        return new CustomClusterRenderer(context, googleMap, clusterManager);
      }
    };
  }
}
