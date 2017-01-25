package agency.tango.viking.example;

import android.content.Context;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActMvvmdemoBinding;
import agency.tango.viking.example.viewmodels.SecondViewModel;
import agency.tango.viking.mvvm.ActivityView;

@AutoModule
public class MvvmSecondActivity extends ActivityView<SecondViewModel, ActMvvmdemoBinding> {

  public MvvmSecondActivity() {
    super(R.layout.act_mvvmdemo);
  }

  @Override
  protected void inject(Context context) {
    App.get(context)
        .getActivityComponentBuilder(MvvmSecondActivity.class,
            MvvmSecondActivity_Component.Builder.class)
        .screenModule(new MvvmSecondActivity_Module(context, this))
        .build()
        .injectMembers(this);
  }

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

  }
}
