package agency.tango.viking.mvp;

import android.os.Bundle;

public interface OnResultAction {
  void execute(boolean success, Bundle dataWrapper);
}