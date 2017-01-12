package agency.tango.viking.annotations;

public interface ActivityComponentBuilder<M, C> {
  ActivityComponentBuilder<M, C> activityModule(M activityModule);

  C build();
}