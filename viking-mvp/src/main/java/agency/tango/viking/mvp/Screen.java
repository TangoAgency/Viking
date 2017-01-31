package agency.tango.viking.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public abstract class Screen<V, P extends Presenter<V>> extends AppCompatActivity {
  private final int layoutResId;

  @Inject
  protected P presenter;

  private PresenterDelegate<P> presenterDelegate;

  protected Screen(int layoutResId) {
    this.layoutResId = layoutResId;
  }

  public P presenter() {
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layoutResId);

    inject(this);
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
