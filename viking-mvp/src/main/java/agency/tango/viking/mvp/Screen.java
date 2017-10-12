package agency.tango.viking.mvp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public abstract class Screen<V, P extends Presenter<V>> extends AppCompatActivity {
  private final int layoutResId;
  private final Class<P> presenterClass;
  private P presenter;
  private PresenterDelegate<P> presenterDelegate;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

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
  protected void onStart() {
    super.onStart();
    presenterDelegate.onStart();
  }

  @Override
  protected void onStop() {
    presenterDelegate.onStop();
    super.onStop();
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

  protected abstract void inject(Context screen);

  protected void onViewReady() {

  }
}
