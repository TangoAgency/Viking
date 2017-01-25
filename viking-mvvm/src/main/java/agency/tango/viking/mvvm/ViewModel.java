package agency.tango.viking.mvvm;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

@SuppressWarnings("Convert2streamapi")
public abstract class ViewModel extends BaseObservable {
  private final SparseArray<OnResultAction> onResultActions = new SparseArray<>();
  private boolean stateWasRestored;

  private final List<StartupAction> startupActions = new ArrayList<>();
  private final List<ViewModel> childViewModels = new ArrayList<>();
  private boolean started = false;

  public ViewModel() {

  }

  @CallSuper
  public void start() {
    if (isFirstRun()) {
      for (StartupAction action : startupActions) {
        action.execute();
      }
      started = true;
    }

    for (int i = 0; i < childViewModels.size(); i++) {
      childViewModels.get(i).start();
    }
  }

  @CallSuper
  public void stop() {
    for (int i = 0; i < childViewModels.size(); i++) {
      childViewModels.get(i).stop();
    }
  }

  @CallSuper
  public void registerChildViewModel(ViewModel viewModel) {
    if (!childViewModels.contains(viewModel)) {
      childViewModels.add(viewModel);
    }
  }

  @CallSuper
  public void unRegisterChildViewModel(ViewModel viewModel) {
    childViewModels.remove(viewModel);
  }

  @CallSuper
  public void onResult(int requestCode, int resultCode, Intent data) {
    executeOnResultActions(requestCode, data, resultCode == RESULT_OK);
  }

  @CallSuper
  void saveState(Bundle outState) {
    for (int i = 0; i < childViewModels.size(); i++) {
      childViewModels.get(i).saveState(outState);
    }
  }

  @CallSuper
  void restoreState(Bundle savedState) {
    for (int i = 0; i < childViewModels.size(); i++) {
      childViewModels.get(i).restoreState(savedState);
    }
    stateWasRestored = true;
  }

  public final void runOnStartup(StartupAction action) {
    if (wasAlreadyStarted()) {
      action.execute();
    } else {
      startupActions.add(action);
    }
  }

  public final void registerOnResultAction(int requestCode, OnResultAction action) {
    onResultActions.put(requestCode, action);
  }

  public final boolean stateWasRestored() {
    return stateWasRestored;
  }

  private boolean isFirstRun() {
    return !started;
  }

  private boolean wasAlreadyStarted() {
    return started;
  }

  private void executeOnResultActions(Integer requestCode, Intent data, boolean result) {
    OnResultAction onResultAction = onResultActions.get(requestCode);
    if (onResultAction != null) {
      onResultActions.get(requestCode).execute(result, data);
    }
  }
}
