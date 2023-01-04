package ml.empee.notifier.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.Configuration;
import ml.empee.configurator.annotations.Path;
import ml.empee.configurator.annotations.Required;
import ml.empee.notifier.ReportLevel;

@Getter @Setter(AccessLevel.PRIVATE)
public final class NotifierConfig extends Configuration {

  @Required
  @Path("notifier.enabled")
  private Boolean enabled;

  @Required
  @Path("notifier.report-level")
  private ReportLevel reportLevel;

  public NotifierConfig() {
    super("notifier.yml", 1);
  }

}
