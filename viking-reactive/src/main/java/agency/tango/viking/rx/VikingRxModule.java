package agency.tango.viking.rx;

import agency.tango.viking.rx.util.BaseSchedulerProvider;
import agency.tango.viking.rx.util.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class VikingRxModule {
  @Provides
  public BaseSchedulerProvider providesMainThreadShecduler() {
    return SchedulerProvider.getInstance();
  }
}
