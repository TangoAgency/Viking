package agency.tango.viking.mvvm;

import android.content.Intent;
import android.os.Bundle;

public class ViewModelDelegate<VM extends ViewModel> {

  private final VM viewModel;

  public ViewModelDelegate(VM viewModel) {
    this.viewModel = viewModel;
  }

  protected void onCreate(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      viewModel.restoreState(savedInstanceState);
    }
  }

  protected void onStart() {
    viewModel.start();
  }

  protected void onStop() {
    viewModel.stop();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    viewModel.onResult(requestCode, resultCode, data);
  }

  protected void onSaveInstanceState(Bundle outState) {
    viewModel.saveState(outState);
  }
}
