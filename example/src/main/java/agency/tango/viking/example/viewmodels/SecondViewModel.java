package agency.tango.viking.example.viewmodels;

import javax.inject.Inject;

import agency.tango.viking.example.MvvmSecondActivity;
import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.mvvm.ViewModel;

public class SecondViewModel extends ViewModel {
  @Inject
  public SecondViewModel(MvvmSecondActivity mvvmDemo2, Navigator navigator) {
  }
}
