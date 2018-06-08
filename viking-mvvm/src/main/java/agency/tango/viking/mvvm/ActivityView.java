package agency.tango.viking.mvvm;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import java.io.Serializable;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class ActivityView<VM extends ViewModel, VD extends ViewDataBinding> extends
    DaggerAppCompatActivity {

  private VM viewModel;

  //@Inject
  //ViewModelProvider.Factory viewModelFactory;

  @Inject
  GenericViewModelFactory<VM> viewModelFactory;

  private VD binding;
  private int layoutIdRes;
  private final Class<VM> viewModelClass;
  private ViewModelDelegate<VM> viewModelDelegate;

  public ActivityView(@LayoutRes int layoutIdRes, Class<VM> viewModelClass) {
    this.layoutIdRes = layoutIdRes;
    this.viewModelClass = viewModelClass;
  }

  public final VM viewModel() {
    return viewModel;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    viewModelDelegate = new ViewModelDelegate<>(viewModel);
    binding = DataBindingUtil.setContentView(this, layoutIdRes);
    bind(binding);

    viewModelDelegate.onCreate(savedInstanceState);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    viewModelDelegate.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    viewModelDelegate.onSaveInstanceState(outState);
  }

  @SuppressWarnings("unchecked")
  protected <T extends FragmentView> T resolveFragment(@IdRes int id) {
    return (T) getSupportFragmentManager().findFragmentById(id);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ViewModel> T resolveChildViewModel(@IdRes int id) {
    return (T) resolveFragment(id).viewModel();
  }

  protected abstract void bind(VD binding);

  protected VD binding() {
    return binding;
  }

  @SuppressWarnings("unchecked")
  protected <T extends Serializable> T getIntentExtra(String key) {
    return (T) getIntent().getSerializableExtra(key);
  }
}
