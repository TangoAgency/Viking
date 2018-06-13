package agency.tango.viking.mvp;

import androidx.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public abstract class Presenter<TView> extends ViewModel implements IPresenter<TView> {

  private final SparseArray<OnResultAction> onResultActions = new SparseArray<>();
  private final List<StartupAction> startupActions = new ArrayList<>();

  protected IPresenter childPresenter;

  private boolean started = false;
  private boolean stateWasRestored;

  @CallSuper
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

  @CallSuper
  @Override
  public void stop() {
    if (childPresenter != null) {
      childPresenter.stop();
    }
  }

  @CallSuper
  @Override
  public void onResult(int requestCode, int resultCode, Intent data) {
    executeOnResultActions(requestCode, data, resultCode == RESULT_OK);
  }

  @CallSuper
  @Override
  public void saveState(Bundle dataWrapper) {

  }

  @CallSuper
  @Override
  public void restoreState(Bundle dataWrapper) {
    stateWasRestored = true;
  }

  public final void registerOnResultAction(int requestCode, OnResultAction action) {
    onResultActions.put(requestCode, action);
  }

  public boolean stateWasRestored() {
    return stateWasRestored;
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

  private void executeOnResultActions(int requestCode, Intent data, boolean result) {
    OnResultAction onResultAction = onResultActions.get(requestCode);
    if (onResultAction != null) {
      onResultActions.get(requestCode).execute(result, data);
    }
  }

  private boolean isFirstRun() {
    return !started;
  }
}