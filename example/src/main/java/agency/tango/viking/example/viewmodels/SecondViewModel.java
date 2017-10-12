package agency.tango.viking.example.viewmodels;

import javax.inject.Inject;

import agency.tango.viking.annotations.ProvidesViewModel;
import agency.tango.viking.mvvm.ViewModel;

@ProvidesViewModel
public class SecondViewModel extends ViewModel {
  @Inject
  public SecondViewModel() {
  }
}
