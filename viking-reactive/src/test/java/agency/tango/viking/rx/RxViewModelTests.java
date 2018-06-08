package agency.tango.viking.rx;

import androidx.databinding.Bindable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import agency.tango.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("WeakerAccess")
@RunWith(MockitoJUnitRunner.class)
public class RxViewModelTests {
  @Mock
  BaseSchedulerProvider baseSchedulerProvider;

  private TestScheduler testScheduler;

  @Before
  public void setUp() {
    testScheduler = new TestScheduler();
    when(baseSchedulerProvider.ui()).thenReturn(testScheduler);
  }

  @Test
  public void viewModel_start_subscribeToObservableAndUpdatesViewModelValues() {

    TestRxViewModel viewModel = new TestRxViewModel(baseSchedulerProvider);

    viewModel.start();

    assertThat(viewModel.disposables.isDisposed()).isFalse();
    assertThat(viewModel.disposables.size()).isEqualTo(1);

    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);
    assertThat(viewModel.aLongValue).isEqualTo(0L);

    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);
    assertThat(viewModel.aLongValue).isEqualTo(1L);

    testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS);
    assertThat(viewModel.aLongValue).isEqualTo(4L);

    assertThat(viewModel.disposables.isDisposed()).isFalse();
    assertThat(viewModel.disposables.size()).isEqualTo(1);
  }

  @Test
  public void viewModel_stop_clearDisposablesStopsUpdatingViewModelValues() {

    TestRxViewModel viewModel = new TestRxViewModel(baseSchedulerProvider);

    viewModel.start();
    viewModel.stop();

    assertThat(viewModel.disposables.isDisposed()).isFalse();
    assertThat(viewModel.disposables.size()).isEqualTo(0);
    assertThat(baseSchedulerProvider).isSameAs(viewModel.schedulerProvider());
  }

  private static class TestRxViewModel extends RxViewModel {

    @Bindable
    public Long aLongValue;

    public TestRxViewModel(BaseSchedulerProvider schedulerProvider) {
      super(schedulerProvider);
    }

    @Override
    public void start() {
      super.start();

      disposables.add(
          Observable.interval(100, 100, TimeUnit.MILLISECONDS, schedulerProvider().ui()).subscribe(
              new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                  aLongValue = aLong;
                }
              }));

    }
  }
}

