package agency.tango.viking.mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import javax.inject.Inject;

public abstract class ActivityView<VM extends ViewModel, VD extends ViewDataBinding> extends
    AppCompatActivity {
  private VD binding;
  private int layoutIdRes;
  private ViewModelDelegate<VM> viewModelDelegate;

  @Inject
  VM viewModel;

  public ActivityView(@LayoutRes int layoutIdRes) {
    this.layoutIdRes = layoutIdRes;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(this);
    viewModelDelegate = new ViewModelDelegate<>(viewModel);
    binding = DataBindingUtil.setContentView(this, layoutIdRes);
    bind(binding);

    viewModelDelegate.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
    viewModelDelegate.onStart();
  }

  @Override
  protected void onStop() {
    viewModelDelegate.onStop();
    super.onStop();
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

  protected abstract void inject(Context context);

  protected abstract void bind(VD binding);

  protected VD binding() {
    return binding;
  }

  public final VM viewModel() {
    return viewModel;
  }

  @SuppressWarnings("unchecked")
  protected <T extends Serializable> T getIntentExtra(String key) {
    return (T) getIntent().getSerializableExtra(key);
  }
}
