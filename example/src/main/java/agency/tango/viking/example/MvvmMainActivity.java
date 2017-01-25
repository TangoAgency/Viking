package agency.tango.viking.example;

import android.content.Context;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActMvvmdemoBinding;
import agency.tango.viking.example.viewmodels.MainViewModel;
import agency.tango.viking.mvvm.ActivityView;

@AutoModule
public class MvvmMainActivity extends ActivityView<MainViewModel, ActMvvmdemoBinding> {

  public MvvmMainActivity() {
    super(R.layout.act_mvvmdemo);
  }

  @Override
  protected void inject(Context context) {
    App.get(context)
        .getActivityComponentBuilder(MvvmMainActivity.class,
            MvvmMainActivity_Component.Builder.class)
        .screenModule(new MvvmMainActivity_Module(context, this))
        .build()
        .injectMembers(this);
  }

  @Override
  protected void bind(ActMvvmdemoBinding binding) {
    binding.setViewModel(viewModel());
  }
}
