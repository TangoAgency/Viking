package agency.tango.viking.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.google.android.gms.maps.MapView;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.annotations.AutoProvides;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.example.dialog.VikingDialogFragment;
import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.map.views.MapAwareActivityView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

@AutoModule
public class MapActivity extends MapAwareActivityView<MapViewModel, ActivityMapBinding> {
  private static final String DIALOG_FRAGMENT_TAG = "fragment_dialog";

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

    Button dialogButton = findViewById(R.id.button_dialog);
    dialogButton.setOnClickListener(listener -> {
      FragmentManager fragmentManager = getSupportFragmentManager();
      VikingDialogFragment fragment = VikingDialogFragment.newInstance();
      fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
    });
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