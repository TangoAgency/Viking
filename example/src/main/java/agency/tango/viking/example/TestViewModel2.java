package agency.tango.viking.example;

import android.util.Log;

import javax.inject.Inject;

import agency.tango.viking.annotations.ProvidesViewModel;
import agency.tango.viking.mvvm.ViewModel;

@ProvidesViewModel
  public  class TestViewModel2 extends ViewModel {

    @Inject
    public TestViewModel2() {
      Log.d("Test", "Hello!");
    }
  }