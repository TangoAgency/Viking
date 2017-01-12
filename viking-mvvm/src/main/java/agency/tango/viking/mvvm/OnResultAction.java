package agency.tango.viking.mvvm;

import android.content.Intent;

public interface OnResultAction {
  void execute(boolean success, Intent data);
}