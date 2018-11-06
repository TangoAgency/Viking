package agency.tango.viking.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import javax.inject.Inject;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatDialogFragment;

public abstract class DialogFragmentView<VM extends ViewModel, VD extends ViewDataBinding>
    extends DaggerAppCompatDialogFragment {

  private VM viewModel;
  private VD binding;
  private int layoutIdRes;
  private final Class<VM> viewModelClass;
  private ViewModelDelegate<VM> viewModelDelegate;

  @Inject
  GenericViewModelFactory<VM> viewModelFactory;

  public DialogFragmentView(@LayoutRes int layoutIdRes, Class<VM> viewModelClass) {
    this.layoutIdRes = layoutIdRes;
    this.viewModelClass = viewModelClass;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(layoutIdRes, container, false);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    viewModelDelegate = new ViewModelDelegate<>(viewModel);
    binding = DataBindingUtil.bind(view);

    bind(binding);

    if (savedInstanceState != null) {
      viewModelDelegate.onCreate(savedInstanceState);
    }

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    viewModelDelegate.onStart();
  }

  @Override
  public void onPause() {
    super.onPause();
    viewModelDelegate.onStop();
  }

  public VM viewModel() {
    return viewModel;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    viewModelDelegate.onSaveInstanceState(outState);
  }

  @SuppressWarnings("unchecked")
  protected <T extends FragmentView> T resolveFragment(@IdRes int id) {
    return (T) getChildFragmentManager().findFragmentById(id);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ViewModel> T resolveChildViewModel(@IdRes int id) {
    return (T) resolveFragment(id).viewModel();
  }

  protected VD binding() {
    return binding;
  }

  protected abstract void bind(VD binding);
}
