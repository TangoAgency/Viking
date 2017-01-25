package agency.tango.viking.mvvm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public abstract class FragmentView<VM extends ViewModel, VD extends ViewDataBinding> extends
    Fragment {
  private VD binding;
  private int layoutIdRes;

  @Inject
  VM viewModel;

  public FragmentView(@LayoutRes int layoutIdRes) {
    this.layoutIdRes = layoutIdRes;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(layoutIdRes, container, false);

    inject(getContext());

    binding = DataBindingUtil.bind(view);

    bind(binding);

    if (savedInstanceState != null) {
      viewModel.restoreState(savedInstanceState);
    }

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    viewModel.start();
  }

  @Override
  public void onStop() {
    viewModel.stop();
    super.onStop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    viewModel.saveState(outState);
  }

  protected abstract void inject(Context screen);

  protected abstract void bind(VD binding);

  public VM viewModel() {
    return viewModel;
  }

  private String resolveScreenName() {
    return getClass().getSimpleName();
  }
}
