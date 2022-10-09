package ml.empee.notifier.configs;

import java.util.Locale;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.ConfigFile;
import ml.empee.configurator.ConfigurationProcessor;
import ml.empee.configurator.annotations.Path;
import ml.empee.notifier.ReportLevel;

@Getter
@Setter(AccessLevel.PRIVATE)
public final class DefaultConfig extends ConfigFile {

  private static DefaultConfig instance;

  public static DefaultConfig getInstance(JavaPlugin plugin) {
    if(instance == null) {
      instance = ConfigurationProcessor.loadConfiguration(new DefaultConfig(plugin));
    }

    return instance;
  }

  private DefaultConfig(JavaPlugin plugin) {
    super(plugin, "notifier.yml", false);
  }

  @Path(value = "notifier.enabled", required = true)
  private Boolean enabled;

  @Path(value = "notifier.report-level", required = true)
  private ReportLevel reportLevel;

  private void setReportLevel(String level) {
    this.reportLevel = ReportLevel.valueOf(level.toUpperCase(Locale.ROOT));
  }

}
