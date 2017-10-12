package agency.tango.viking.example.di;

import android.app.Application;
import android.content.Context;

import net.droidlabs.dagger.annotations.AppScope;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

  @Provides
  @AppScope
  Context provideContext(Application application) {
    return application;
  }
}
