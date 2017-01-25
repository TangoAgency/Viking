package agency.tango.viking.di;

public interface HasScreenSubcomponentBuilders {
  <T extends ScreenComponentBuilder> T getActivityComponentBuilder(Class<?> activityClass,
      Class<T> compotentBuilderType);
}