package agency.tango.viking.example;

import android.os.Bundle;

import com.google.android.gms.maps.MapView;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.map.views.MapAwareActivityView;

@AutoModule
public class MapActivity extends MapAwareActivityView<MapViewModel, ActivityMapBinding> {

  public MapActivity() {
    super(R.layout.activity_map, MapViewModel.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragmentBla, MvvmFrament.newInstance())
          .commitAllowingStateLoss();
    }
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