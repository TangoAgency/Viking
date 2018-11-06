package agency.tango.viking.example.dialog.mvp;

import javax.inject.Inject;
import agency.tango.viking.mvp.Presenter;

public class VikingDialogPresenter extends Presenter<VikingDialogContract.View> implements VikingDialogContract.Presenter {
  @Inject
  public VikingDialogPresenter() { }
}
