package agency.tango.viking.annotations;

import dagger.MapKey;

@MapKey
public @interface ScreenKey {
  Class<?> value();
}
