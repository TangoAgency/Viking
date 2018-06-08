package agency.tango.viking.example;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.databinding.FragmentTestBinding;
import agency.tango.viking.mvvm.FragmentView;

@AutoModule(scopes = {MapActivity.class})
public class MvvmFragment extends FragmentView<TestViewModel2, FragmentTestBinding> {

  public static MvvmFragment newInstance() {
    return new MvvmFragment();
  }

  public MvvmFragment() {
    super(R.layout.fragment_test, TestViewModel2.class);
  }

  @Override
  protected void bind(FragmentTestBinding binding) {

  }
}
