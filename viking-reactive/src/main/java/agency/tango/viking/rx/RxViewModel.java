package agency.tango.viking.rx;

import agency.tango.viking.mvvm.ViewModel;
import agency.tango.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class RxViewModel extends ViewModel {

  private final BaseSchedulerProvider schedulerProvider;
  protected final CompositeDisposable disposables;

  public RxViewModel(BaseSchedulerProvider schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
    this.disposables = new CompositeDisposable();
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
