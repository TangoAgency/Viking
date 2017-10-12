package agency.tango.viking.example;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.ActMvvmdemoBinding;
import agency.tango.viking.example.viewmodels.SecondViewModel;
import agency.tango.viking.mvvm.ActivityView;

@AutoModule
public class MvvmSecondActivity extends ActivityView<SecondViewModel, ActMvvmdemoBinding> {

  public MvvmSecondActivity() {
    super(R.layout.act_mvvmdemo, SecondViewModel.class);
  }

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

  }
}
