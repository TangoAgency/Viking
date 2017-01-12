package agency.tango.viking.mvp;

import android.os.Bundle;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public abstract class Presenter<TView> implements IPresenter {
  public static final boolean SUCCESS = true;
  public static final boolean FAILURE = false;

  protected IPresenter childPresenter;

  private boolean started = false;
  private final SparseArray<OnResultAction> onResultActions = new SparseArray<>();
  private final List<StartupAction> startupActions = new ArrayList<>();
  private boolean stateWasRestored;

  public Presenter() {

  }

  public void start() {
    if (childPresenter != null) {
      childPresenter.start();
    }

    if (isFirstRun()) {
      for (StartupAction action : startupActions) {
        action.execute();
      }
      started = true;
    }
  }

  private boolean isFirstRun() {
    return started == false;
  }

  @Override
  public void stop() {
    if (childPresenter != null) {
      childPresenter.stop();
    }
  }

  protected void runOnStartup(StartupAction action) {
    if (started) {
      action.execute();
    } else {
      startupActions.add(action);
    }
  }

  protected void changePresentation(IPresenter childPresenter) {
    if (this.childPresenter != null && started) {
      this.childPresenter.stop();
    }

    this.childPresenter = childPresenter;

    if (this.childPresenter != null && started) {
      this.childPresenter.start();
    }
  }

  @Override
  public final void onResult(int requestCode, Bundle dataWrapper) {
    executeOnResultActions(requestCode, dataWrapper, SUCCESS);
  }

  @Override
  public final void onResultCanceled(int requestCode, Bundle dataWrapper) {
    executeOnResultActions(requestCode, dataWrapper, FAILURE);
  }

  public boolean stateWasRestored() {
    return stateWasRestored;
  }

  @Override
  public void saveState(Bundle dataWrapper) {

  }

  @Override
  public void restoreState(Bundle dataWrapper) {
    stateWasRestored = true;
  }

  private void executeOnResultActions(Integer requestCode, Bundle dataWrapper, boolean result) {
    OnResultAction onResultAction = onResultActions.get(requestCode);
    if (onResultAction != null) {
      onResultAction.execute(result, dataWrapper);
    }
  }

  public final void registerOnResultAction(int requestCode, OnResultAction action) {
    onResultActions.put(requestCode, action);
  }
}