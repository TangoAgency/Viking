package agency.tango.viking.example;

import agency.tango.viking.bindings.map.adapters.IMapItemAdapter;
import agency.tango.viking.mvvm.ViewModel;
import agency.tango.viking.rx.util.SchedulerProvider;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class MapViewModel extends ViewModel
{
    public static final float DEFAULT_ZOOM = 15;

    private final Collection<ExampleModel> models = new ObservableArrayList<>();
    private Location location;
    private float zoom = DEFAULT_ZOOM;

    @Inject
    public MapViewModel()
    {
        Observable.just(1).delay(10, TimeUnit.SECONDS, SchedulerProvider.getInstance()
                .computation())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(new Consumer<Integer>()
                {
                    @Override
                    public void accept(Integer integer) throws Exception
                    {
                        models.add(new ExampleModel(new LatLng(0, 0)));
                    }
                });
    }
    @Bindable
    public Collection<ExampleModel> getModels()
    {
        return models;
    }

    @Bindable
    public Location getLocation()
    {
        return this.location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
        this.notifyPropertyChanged(BR.location);
    }

    @Bindable
    public float getZoom()
    {
        return zoom;
    }

    public void setZoom(float zoom)
    {
        this.zoom = zoom;
        notifyPropertyChanged(BR.zoom);
    }

    @Bindable
    public IMapItemAdapter<ExampleModel> getAdapter()
    {
        return new IMapItemAdapter<ExampleModel>()
        {
            @Override
            public int icon(ExampleModel item)
            {
                return R.drawable.amu_bubble_mask;
            }

            @Override
            public LatLng position(ExampleModel item)
            {
                return item.getLatLng();
            }
        };
    }

}
