package agency.tango.viking.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public abstract class FragmentScreen<V, P extends Presenter<V>>
    extends Fragment {

  private final int layoutResId;

  @Inject
  protected P presenter;

  private PresenterDelegate<P> presenterDelegate;

  protected FragmentScreen(int layoutResId) {
    this.layoutResId = layoutResId;
  }

  public P presenter() {
    return presenter;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(getActivity());
    presenterDelegate.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    View view = inflater.inflate(layoutResId, container, false);

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
