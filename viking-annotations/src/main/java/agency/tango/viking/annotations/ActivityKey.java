package agency.tango.viking.annotations;

import dagger.MapKey;

@MapKey
public @interface ActivityKey {
  Class<?> value();
}
