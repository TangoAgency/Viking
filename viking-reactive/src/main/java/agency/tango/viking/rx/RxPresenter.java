package agency.tango.viking.rx;

import agency.tango.viking.mvp.Presenter;
import agency.tango.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class RxPresenter<T> extends Presenter<T> {

  private final BaseSchedulerProvider schedulerProvider;
  protected final CompositeDisposable disposables;

  public RxPresenter(BaseSchedulerProvider schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
    disposables = new CompositeDisposable();
  }

  public BaseSchedulerProvider schedulerProvider() {
    return schedulerProvider;
  }

  @Override
  public void stop() {
    super.stop();
    disposables.clear();
  }
}
