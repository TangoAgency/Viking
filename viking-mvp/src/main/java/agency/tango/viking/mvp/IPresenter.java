package agency.tango.viking.mvp;

import android.os.Bundle;

public interface IPresenter
{
    void start();

    void stop();

    void onResult(int requestCode, Bundle dataWrapper);

    void onResultCanceled(int requestCode, Bundle dataWrapper);

    void saveState(Bundle dataWrapper);

    void restoreState(Bundle dataWrapper);

    class DefaultPresenter implements IPresenter
    {

        @Override
        public void start()
        {

        }

        @Override
        public void stop()
        {

        }

        @Override
        public void onResult(int requestCode, Bundle dataWrapper)
        {

        }

        @Override
        public void onResultCanceled(int requestCode, Bundle dataWrapper)
        {

        }

        @Override
        public void saveState(Bundle dataWrapper)
        {

        }

        @Override
        public void restoreState(Bundle dataWrapper)
        {

        }
    }
}