package agency.tango.viking.example.di;

import android.arch.lifecycle.ViewModelProvider;

import agency.tango.viking.example.arch.ViewModelFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class TestModule {

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
