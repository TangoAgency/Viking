package net.droidlabs.dagger.annotations;

import dagger.MapKey;

@MapKey
public @interface ActivityKey {
  Class<?> value();
}
