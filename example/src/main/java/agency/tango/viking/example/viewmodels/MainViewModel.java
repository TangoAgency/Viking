package agency.tango.viking.example.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import javax.inject.Inject;

import agency.tango.viking.annotations.ProvidesViewModel;
import agency.tango.viking.example.SingleLiveEvent;
import agency.tango.viking.example.services.Navigator;
import agency.tango.viking.mvvm.ViewModel;

@ProvidesViewModel
public class MainViewModel extends ViewModel {
  private Navigator navigator;

  MutableLiveData<Integer> test = new SingleLiveEvent<>();
  private int value = 0;

  public LiveData<Integer> getTest() {
    return test;
  }

  @Inject
  public MainViewModel() {

  }

  public void click() {
    Log.d("test", "Click");
    test.postValue(value);
  }

}
