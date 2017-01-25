package agency.tango.viking.example;

import android.app.Application;
import android.content.Context;

import java.util.Map;

import javax.inject.Inject;

import agency.tango.viking.di.ScreenComponentBuilder;
import agency.tango.viking.di.HasScreenSubcomponentBuilders;
import agency.tango.viking.example.di.DiComponent;

public class App extends Application implements HasScreenSubcomponentBuilders {

  private DiComponent component;

  @Inject
  Map<Class<?>, ScreenComponentBuilder> activityComponentBuilders;

  public static DiComponent component(Context context) {
    return ((App) context.getApplicationContext()).component;
  }

  public static HasScreenSubcomponentBuilders get(Context context) {
    return ((HasScreenSubcomponentBuilders) context.getApplicationContext());
  }

  public void setComponent(DiComponent diComponent) {
    component = diComponent;

  }
  @SuppressWarnings("unchecked")
  @Override
  public <T extends ScreenComponentBuilder> T getActivityComponentBuilder(Class<?> activityClass,
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
