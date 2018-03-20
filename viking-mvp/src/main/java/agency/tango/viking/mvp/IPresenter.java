package agency.tango.viking.mvp;

import android.content.Intent;
import android.os.Bundle;

public interface IPresenter<TView> {

  void start();

  void stop();

  void onResult(int requestCode, int resultCode, Intent data);

  void saveState(Bundle dataWrapper);

  void restoreState(Bundle dataWrapper);

  class DefaultPresenter implements IPresenter {

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void saveState(Bundle dataWrapper) {

    }

    @Override
    public void restoreState(Bundle dataWrapper) {

    }
  }
}