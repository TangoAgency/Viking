package agency.tango.viking.rx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import agency.tango.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("WeakerAccess")
@RunWith(MockitoJUnitRunner.class)
public class RxPresenterTests {
  @Mock
  BaseSchedulerProvider baseSchedulerProvider;

  @Spy
  TestView testViewSpy = new TestView();

  private TestScheduler testScheduler;

  @Before
  public void setUp() {
    testScheduler = new TestScheduler();
    when(baseSchedulerProvider.ui()).thenReturn(testScheduler);
  }

  @Test
  public void presenter_start_subscribeToObservableAndUpdatesView() {
    TestRxPresenter presenter = new TestRxPresenter(baseSchedulerProvider, testViewSpy);

    presenter.start();

    assertThat(presenter.disposables.isDisposed(), is(false));
    assertThat(presenter.disposables.size(), is(1));

    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);
    verify(testViewSpy, times(1)).displayLong(0L);

    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);
    verify(testViewSpy, times(1)).displayLong(1L);

    testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS);
    verify(testViewSpy, times(1)).displayLong(2L);
    verify(testViewSpy, times(1)).displayLong(3L);
    verify(testViewSpy, times(1)).displayLong(4L);
  }

  @Test
  public void presenter_stop_clearDisposablesAndStopsUpdatingView() {
    TestRxPresenter presenter = new TestRxPresenter(baseSchedulerProvider, testViewSpy);

    presenter.start();
    presenter.stop();

    testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS);
    verify(testViewSpy, never()).displayLong(anyLong());

    assertThat(presenter.disposables.isDisposed(), is(false));
    assertThat(presenter.disposables.size(), is(0));
  }

  private static class TestView {
    public void displayLong(Long aLong) {
      //intentionally does nothing
    }
  }

  private static class TestRxPresenter extends RxPresenter<TestView> {
    private final TestView testView;

    public TestRxPresenter(BaseSchedulerProvider schedulerProvider, TestView testView) {
      super(schedulerProvider);
      this.testView = testView;
    }

    @Override
    public void start() {
      super.start();

      disposables.add(
          Observable.interval(100, 100, TimeUnit.MILLISECONDS, schedulerProvider().ui())
              .subscribe(
                  new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                      testView.displayLong(aLong);
                    }
                  }));

    }
  }
}
