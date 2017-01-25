package agency.tango.viking.example.viewmodels;

import javax.inject.Inject;

import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.mvvm.ViewModel;

public class MainViewModel extends ViewModel {
  private Navigator navigator;

  @Inject
  public MainViewModel(Navigator navigator) {
    this.navigator = navigator;
  }

  public void click() {
    navigator.openSecondActivity();
  }

}
