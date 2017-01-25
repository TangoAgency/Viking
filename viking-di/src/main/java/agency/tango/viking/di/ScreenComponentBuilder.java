package agency.tango.viking.di;

public interface ScreenComponentBuilder<M, C> {
  ScreenComponentBuilder<M, C> screenModule(M screenModule);

  C build();
}