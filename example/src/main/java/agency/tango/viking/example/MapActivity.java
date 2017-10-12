package agency.tango.viking.example;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.MapView;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.map.views.MapAwareActivityView;
import dagger.android.support.HasSupportFragmentInjector;
@AutoModule
public class MapActivity extends MapAwareActivityView<MapViewModel, ActivityMapBinding> implements
    HasSupportFragmentInjector {


  public MapActivity() {
    super(R.layout.activity_map, MapViewModel.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragmentBla, MvvmFrament.newInstance())
          .commitAllowingStateLoss();
    }

    getApplicationContext().startActivity(new Intent(getApplicationContext(), MvvmMainActivity.class));
  }

  @Override
  protected void bind(ActivityMapBinding binding) {

    binding.setViewModel(viewModel());
  }

  @Override
  public MapView mapView() {
    return binding().map;
  }

  @Override
  public void onDestroy() {
    binding().map.clear();
    super.onDestroy();
  }
}