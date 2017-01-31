package agency.tango.viking.mvp;

import android.content.Intent;

public interface OnResultAction {
  void execute(boolean success, Intent data);
}