package agency.tango.viking.example;

import agency.tango.viking.example.databinding.FragmentTestBinding;
import agency.tango.viking.mvvm.FragmentView;

public class MvvmFrament extends FragmentView<TestViewModel2, FragmentTestBinding> {

  public static MvvmFrament newInstance() {
    return new MvvmFrament();
  }

  public MvvmFrament() {
    super(R.layout.fragment_test, TestViewModel2.class);
  }

  @Override
  protected void bind(FragmentTestBinding binding) {

  }
}
