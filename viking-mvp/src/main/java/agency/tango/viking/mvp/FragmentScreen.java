package agency.tango.viking.mvp;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
  GenericViewModelFactory<P> viewModelFactory;

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
      Bundle savedInstanceState) {
    View view = inflater.inflate(layoutResId, container, false);

    presenter = ViewModelProviders.of(this, viewModelFactory).get(presenterClass);
    presenterDelegate = new PresenterDelegate<>(presenter);

    if (savedInstanceState != null) {
      presenterDelegate.onCreate(savedInstanceState);
    }

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    onViewReady();
  }

  @Override
  public void onResume() {
    super.onResume();
    presenterDelegate.onStart();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenterDelegate.onStop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenterDelegate.onSaveInstanceState(outState);
  }

  protected void onViewReady() {

  }
}
