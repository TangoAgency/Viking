package agency.tango.viking.di;

import android.content.Context;

import net.droidlabs.dagger.annotations.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public abstract class ScreenModule<T> {
  private final Context context;
  protected final T screen;

  public ScreenModule(Context context, T screen) {
    this.context = context;
    this.screen = screen;
  }

  @ActivityScope
  @Provides
  public Context providesContext() {
    return this.context;
  }

  @ActivityScope
  @Provides
  public T providesScreen() {
    return this.screen;
  }
}
