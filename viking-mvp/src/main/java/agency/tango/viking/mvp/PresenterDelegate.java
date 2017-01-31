package agency.tango.viking.mvp;

import android.content.Intent;
import android.os.Bundle;

public class PresenterDelegate<P extends Presenter> {

  private final P presenter;

  public PresenterDelegate(P presenter) {
    this.presenter = presenter;
  }

  protected void onCreate(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      presenter.restoreState(savedInstanceState);
    }
  }

  protected void onStart() {
    presenter.start();
  }

  protected void onStop() {
    presenter.stop();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   presenter.onResult(requestCode, resultCode, data);
  }

  protected void onSaveInstanceState(Bundle outState) {
    presenter.saveState(outState);
  }
}
