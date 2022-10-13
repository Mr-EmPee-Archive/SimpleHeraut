package ml.empee.notifier.configs;

import java.util.Locale;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.ConfigFile;
import ml.empee.configurator.annotations.Path;
import ml.empee.notifier.ReportLevel;

@Getter
@Setter(AccessLevel.PRIVATE)
public final class NotifierConfig extends ConfigFile {

  @Path(value = "notifier.enabled", required = true)
  private Boolean enabled;

  @Path(value = "notifier.report-level", required = true)
  private ReportLevel reportLevel;

  public NotifierConfig(JavaPlugin plugin) {
    super(plugin, "notifier.yml");

    registerUpdate(new ConfigUpdaterTo_V1());
  }

  private void setReportLevel(String level) {
    this.reportLevel = ReportLevel.valueOf(level.toUpperCase(Locale.ROOT));
  }

}
