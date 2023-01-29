package ml.empee.notifier.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.Configuration;
import ml.empee.configurator.annotations.Path;
import ml.empee.configurator.annotations.Required;
import ml.empee.notifier.ReportLevel;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter(AccessLevel.PRIVATE)
public final class NotifierConfig extends Configuration {

  @Required
  @Path("notifier.enabled")
  private Boolean enabled;

  @Required
  @Path("notifier.report-level")
  private ReportLevel reportLevel;

  public NotifierConfig(JavaPlugin plugin) {
    super(plugin, "notifier.yml", 1);
  }

}
