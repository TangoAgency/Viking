package agency.tango.viking.example;

import android.content.Context;

import com.google.android.gms.maps.MapView;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.map.views.MapAwareActivityView;

@AutoModule
public class MapActivity extends MapAwareActivityView<MapViewModel, ActivityMapBinding> {

  public MapActivity() {
    super(R.layout.activity_map);
  }

  @Override
  protected void inject(Context context) {
    App.get(context)
        .getActivityComponentBuilder(MapActivity.class, MapActivity_Component.Builder.class)
        .build()
        .injectMembers(this);
  }

  @Override
  protected void bind(ActivityMapBinding binding) {
    binding.setViewModel(viewModel());
  }

  @Override
  public MapView mapView() {
    return binding().map;
  }
}