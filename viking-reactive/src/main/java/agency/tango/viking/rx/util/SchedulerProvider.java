package agency.tango.viking.rx.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides different types of schedulers.
 */
public class SchedulerProvider implements BaseSchedulerProvider {

  @Nullable
  private static SchedulerProvider instance;

  // Prevent direct instantiation.
  private SchedulerProvider() {
  }

  public static synchronized SchedulerProvider getInstance() {
    if (instance == null) {
      instance = new SchedulerProvider();
    }
    return instance;
  }

  @Override
  @NonNull
  public Scheduler computation() {
    return Schedulers.computation();
  }

  @Override
  @NonNull
  public Scheduler io() {
    return Schedulers.io();
  }

  @Override
  @NonNull
  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}