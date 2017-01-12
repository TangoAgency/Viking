package agency.tango.viking.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class Screen<V, P extends Presenter<V>> extends AppCompatActivity {
  private final int layoutResId;

  protected P presenter;

  protected Screen(int layoutResId) {
    this.layoutResId = layoutResId;
  }

  public IPresenter presenter() {
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layoutResId);

    inject(this);

    onViewReady();

    if (savedInstanceState != null) {
      presenter.restoreState(savedInstanceState);
    }
  }

  protected abstract void inject(Context screen);

  protected void onViewReady() {

  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.start();
  }

  @Override
  protected void onPause() {
    super.onPause();
    presenter.stop();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (resultCode) {
      case RESULT_CANCELED:
        presenter.onResultCanceled(requestCode, data.getExtras());
        break;
      case RESULT_OK:
        presenter.onResult(requestCode, data.getExtras());
        break;
      default:
        break;
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenter.saveState(outState);
  }
}
