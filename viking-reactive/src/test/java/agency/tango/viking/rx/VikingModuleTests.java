package agency.tango.viking.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import agency.tango.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class VikingModuleTests {
  @Test
  public void vikingRxModule_returnsProperScheduler() {
    VikingRxModule vikingRxModule = new VikingRxModule();

    BaseSchedulerProvider baseSchedulerProvider = vikingRxModule.providesMainThreadShecduler();

    assertThat(baseSchedulerProvider.computation()).isSameAs(Schedulers.computation());
    assertThat(baseSchedulerProvider.ui()).isSameAs(AndroidSchedulers.mainThread());
    assertThat(baseSchedulerProvider.io()).isSameAs(Schedulers.io());
  }
}
