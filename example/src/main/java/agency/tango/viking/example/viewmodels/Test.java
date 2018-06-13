package agency.tango.viking.example.viewmodels;

import androidx.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;

public class Test extends ViewModel {
  @Inject
  public Test() {
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    Log.d("AA", "Cleared");
  }
}
