package agency.tango.viking.example.dialog.mvvm;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.R;
import agency.tango.viking.example.TestViewModel2;
import agency.tango.viking.example.databinding.FragmentVikingDialogBinding;
import agency.tango.viking.mvvm.DialogFragmentView;

@AutoModule
public class DialogMvvmFragment extends DialogFragmentView<TestViewModel2, FragmentVikingDialogBinding> {
  public static DialogMvvmFragment newInstance() {
    return new DialogMvvmFragment();
  }

  public DialogMvvmFragment() {
    super(R.layout.fragment_viking_dialog, TestViewModel2.class);
  }

  @Override
  protected void bind(FragmentVikingDialogBinding binding) {

  }
}
