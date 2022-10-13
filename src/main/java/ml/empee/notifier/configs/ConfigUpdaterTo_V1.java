package ml.empee.notifier.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import ml.empee.configurator.ConfigurationUpdate;

public class ConfigUpdaterTo_V1 extends ConfigurationUpdate {
  protected ConfigUpdaterTo_V1() {
    super(0, "notifier.yml");
  }

  @Override
  protected void runUpdate(YamlConfiguration config) {
    String reportLevel = config.getString("notifier.report-level");
    if ("INFO".equalsIgnoreCase(reportLevel)) {
      config.set("notifier.report-level", "UPDATE");
    }
  }
}
