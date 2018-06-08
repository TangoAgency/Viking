package agency.tango.viking.mvp;

import android.content.Intent;
import android.os.Bundle;
import javax.inject.Inject;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class Screen<V, P extends Presenter<V>> extends DaggerAppCompatActivity {
  private final int layoutResId;
  private final Class<P> presenterClass;
  private P presenter;
  private PresenterDelegate<P> presenterDelegate;

  //@Inject
  //ViewModelProvider.Factory viewModelFactory;

  @Inject
  GenericViewModelFactory<P> viewModelFactory;

  protected Screen(int layoutResId, Class<P> presenterClass) {
    this.layoutResId = layoutResId;
    this.presenterClass = presenterClass;
  }

  public P presenter() {
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layoutResId);

    presenter = ViewModelProviders.of(this, viewModelFactory).get(presenterClass);
    presenterDelegate = new PresenterDelegate<>(presenter);

    onViewReady();

    if (savedInstanceState != null) {
      presenter.restoreState(savedInstanceState);
    }
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
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    presenterDelegate.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenterDelegate.onSaveInstanceState(outState);
  }

  protected void onViewReady() {

  }
}
