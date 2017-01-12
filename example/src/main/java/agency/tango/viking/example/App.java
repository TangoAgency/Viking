package agency.tango.viking.example;

import android.app.Application;
import android.content.Context;

import java.util.Map;

import javax.inject.Inject;

import agency.tango.viking.annotations.ActivityComponentBuilder;
import agency.tango.viking.annotations.HasActivitySubcomponentBuilders;

public class App extends Application implements HasActivitySubcomponentBuilders {

  private DiComponent component;

  @Inject
  Map<Class<?>, ActivityComponentBuilder> activityComponentBuilders;

  public static DiComponent component(Context context) {
    return ((App) context.getApplicationContext()).component;
  }

  public static HasActivitySubcomponentBuilders get(Context context) {
    return ((HasActivitySubcomponentBuilders) context.getApplicationContext());
  }

  public void setComponent(DiComponent diComponent) {
    component = diComponent;

  }
  @SuppressWarnings("unchecked")
  @Override
  public <T extends ActivityComponentBuilder> T getActivityComponentBuilder(Class<?> activityClass,
      Class<T> compotentBuilderType) {
    return (T) activityComponentBuilders.get(activityClass);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    component = DiComponent.Initializer.init(this);
    component.inject(this);

  }
}
