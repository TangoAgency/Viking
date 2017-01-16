package agency.tango.viking.example;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
  private final ObservableList<BindablePolyline> paths = new ObservableArrayList<>();
  private final ObservableList<BindableOverlay> overlays = new ObservableArrayList<>();

  private Location location;
  private float zoom = DEFAULT_ZOOM;

  @Inject
  public MapViewModel() {
  }

  @Override
  public void start() {
    super.start();

    models.add(new BindableMarker<>(
        new ExampleModel(new LatLng(0, 0)),
        0,
        new MarkerOptions().position(new LatLng(0, 0))));

    paths.add(new BindablePolyline(0,
        new PolylineOptions().add(new LatLng(0, 0)).add(new LatLng(50, 50))));

    overlays.add(new BindableOverlay(0,
        new GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.amu_bubble_mask))
            .positionFromBounds(new LatLngBounds(new LatLng(0, 0), new LatLng(5, 5)))));

    Observable.just(1).delay(4, TimeUnit.SECONDS, SchedulerProvider.getInstance()
        .computation())
        .observeOn(SchedulerProvider.getInstance().ui())
        .subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {

            models.set(0, new BindableMarker<>(
                new ExampleModel(new LatLng(50, 50)),
                0,
                new MarkerOptions().position(new LatLng(50, 50))));

            paths.set(0, new BindablePolyline(0,
                new PolylineOptions().add(new LatLng(25, 14)).add(new LatLng(25, 50))));

            overlays.set(0, new BindableOverlay(0,
                new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.amu_bubble_mask))
                    .positionFromBounds(new LatLngBounds(new LatLng(5, 10), new LatLng(10, 15)))));
          }
        });
  }

  @Bindable
  public Collection<BindableMarker<ExampleModel>> getModels() {
    return models;
  }

  @Bindable
  public Collection<BindablePolyline> getPaths() {
    return paths;
  }

  @Bindable
  public Collection<BindableOverlay> getOverlays() {return overlays;}

  @Bindable
  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
    this.notifyPropertyChanged(BR.location);
  }

  @Bindable
  public float getZoom() {
    return zoom;
  }

  public void setZoom(float zoom) {
    this.zoom = zoom;
    notifyPropertyChanged(BR.zoom);
  }
}
