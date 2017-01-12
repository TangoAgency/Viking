package agency.tango.viking.example;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActMvvmdemoBinding;
import agency.tango.viking.mvvm.ActivityView;
import android.content.Context;

@AutoModule
public class MvvmDemo extends ActivityView<DemoViewModel, ActMvvmdemoBinding> {

  public MvvmDemo() {
    super(R.layout.act_mvvmdemo);
  }

  @Override
  protected void inject(Context context) {
    App.get(context)
        .getActivityComponentBuilder(MvvmDemo.class, MvvmDemo_Component.Builder.class)
        .build()
        .injectMembers(this);
  }

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

  }
}
