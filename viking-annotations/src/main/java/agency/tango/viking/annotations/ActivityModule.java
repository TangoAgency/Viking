package agency.tango.viking.annotations;

import net.droidlabs.dagger.annotations.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ActivityModule<T> {
  protected final T activity;

  public ActivityModule(T activity) {
    this.activity = activity;
  }

  @Provides
  @ActivityScope
  public T provideActivity() {
    return activity;
  }
}
