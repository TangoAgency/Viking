package agency.tango.viking.annotations;

public interface HasActivitySubcomponentBuilders {
  <T extends ActivityComponentBuilder> T getActivityComponentBuilder(Class<?> activityClass,
      Class<T> compotentBuilderType);
}