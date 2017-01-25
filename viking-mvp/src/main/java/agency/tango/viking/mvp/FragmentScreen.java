package agency.tango.viking.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentScreen<V, P extends Presenter<V>>
    extends Fragment {

  private final int layoutResId;
  protected P presenter;

  protected FragmentScreen(int layoutResId) {
    this.layoutResId = layoutResId;
  }

  public IPresenter presenter() {
    return presenter;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(getActivity());
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(layoutResId, container, false);

    onViewReady();

    return view;
  }

  protected abstract void inject(Context context);

  protected void onViewReady() {

  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.start();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private String resolveScreenName() {
    return getClass().getSimpleName();
  }

  protected abstract IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState);
}
