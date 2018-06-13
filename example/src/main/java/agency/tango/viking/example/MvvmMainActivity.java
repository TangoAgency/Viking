package agency.tango.viking.example;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActMvvmdemoBinding;
import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.example.viewmodels.MainViewModel;
import agency.tango.viking.mvvm.ActivityView;

@AutoModule
public class MvvmMainActivity extends ActivityView<MainViewModel, ActMvvmdemoBinding> {

  public MvvmMainActivity() {
    super(R.layout.act_mvvmdemo, MainViewModel.class);
  }

  @Inject
  Navigator navigator;

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

    viewModel().getTest().observe(this, new Observer<Integer>() {
      @Override
      public void onChanged(@Nullable Integer integer) {
        Log.d("test", String.format("%s", integer));
        navigator.openSecondActivity();
      }
    });

    binding.setViewModel(viewModel());
  }
}
