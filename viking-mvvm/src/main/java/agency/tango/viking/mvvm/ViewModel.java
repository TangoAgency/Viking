package agency.tango.viking.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.CallSuper;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import static android.app.Activity.RESULT_OK;

@SuppressWarnings("Convert2streamapi")
public abstract class ViewModel extends androidx.lifecycle.ViewModel implements Observable {

  private final SparseArray<OnResultAction> onResultActions = new SparseArray<>();
  private final List<StartupAction> startupActions = new ArrayList<>();
  private final List<ViewModel> childViewModels = new ArrayList<>();

  private boolean stateWasRestored;
  private boolean started = false;

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
  public void saveState(Bundle outState) {
    for (int i = 0; i < childViewModels.size(); i++) {
      childViewModels.get(i).saveState(outState);
    }
  }

  @CallSuper
  public void restoreState(Bundle savedState) {
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

  //region Observable
    private transient PropertyChangeRegistry mCallbacks;

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
      synchronized (this) {
        if (mCallbacks == null) {
          mCallbacks = new PropertyChangeRegistry();
        }
      }
      mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
      synchronized (this) {
        if (mCallbacks == null) {
          return;
        }
      }
      mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
  public void notifyChange() {
    synchronized (this) {
      if (mCallbacks == null) {
        return;
      }
    }
    mCallbacks.notifyCallbacks(this, 0, null);
  }

  /**
   * Notifies listeners that a specific property has changed. The getter for the property
   * that changes should be marked with {@link Bindable} to generate a field in
   * <code>BR</code> to be used as <code>fieldId</code>.
   *
   * @param fieldId The generated BR id for the Bindable field.
   */
  public void notifyPropertyChanged(int fieldId) {
    synchronized (this) {
      if (mCallbacks == null) {
        return;
      }
    }
    mCallbacks.notifyCallbacks(this, fieldId, null);
  }

  //endregion
}
