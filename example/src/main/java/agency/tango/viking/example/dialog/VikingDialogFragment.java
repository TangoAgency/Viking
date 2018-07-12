package agency.tango.viking.example.dialog;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.example.R;
import agency.tango.viking.mvp.DialogFragmentScreen;

@AutoModule
public class VikingDialogFragment extends DialogFragmentScreen<VikingDialogContract.View, VikingDialogPresenter>
    implements VikingDialogContract.View {

  public static VikingDialogFragment newInstance() {
    return new VikingDialogFragment();
  }

  public VikingDialogFragment() {
    super(R.layout.fragment_viking_dialog, VikingDialogPresenter.class);
  }
}
