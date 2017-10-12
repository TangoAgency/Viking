package agency.tango.viking.mvp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class FragmentScreen<V, P extends Presenter<V>>
    extends DaggerFragment {

  private final int layoutResId;
  private PresenterDelegate<P> presenterDelegate;
  private final Class<P> presenterClass;
  private P presenter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  protected FragmentScreen(int layoutResId, Class<P> presenterClass) {
    this.layoutResId = layoutResId;
    this.presenterClass = presenterClass;
  }

  public P presenter() {
    return presenter;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    View view = inflater.inflate(layoutResId, container, false);

    presenter = ViewModelProviders.of(this).get(presenterClass);

    if (savedInstanceState != null) {
      presenterDelegate.onCreate(savedInstanceState);
    }

    onViewReady();

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    presenterDelegate.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenterDelegate.onStop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenterDelegate.onSaveInstanceState(outState);
  }

  protected void onViewReady() {

  }

  protected abstract void inject(Context context);
}
