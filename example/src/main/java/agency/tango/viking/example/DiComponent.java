package agency.tango.viking.example;

import net.droidlabs.dagger.annotations.AppScope;

import dagger.Component;
import agency.tango.viking.di.ScreenBindingsModule;

@AppScope
@Component(modules = {
    ScreenBindingsModule.class
})
public interface DiComponent {
  void inject(App app);

  final class Initializer {
    private Initializer() {

    }

    public static DiComponent init(App app) {
      return DaggerDiComponent.builder()
          .build();
    }
  }
}
