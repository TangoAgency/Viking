package agency.tango.viking.example;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.maps.MapView;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.annotations.AutoProvides;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.example.services.Navigator;
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
          .add(R.id.fragmentBla, MvvmFragment.newInstance())
          .commitAllowingStateLoss();
    }
  }

  @Override
  protected void bind(ActivityMapBinding binding) {
    binding.setViewModel(viewModel());

    viewModel().test.observe(this, new Observer<NavigatorOperation>() {
      @Override
      public void onChanged(@Nullable NavigatorOperation integer) {
        Log.d("test", String.format("%s", integer));
        integer.navigate(new Navigator(MapActivity.this));
      }
    });

  }

  @Override
  public MapView mapView() {
    return binding().map;
  }

  @AutoProvides
  public String test() {
    return "Hello world";
  }
}