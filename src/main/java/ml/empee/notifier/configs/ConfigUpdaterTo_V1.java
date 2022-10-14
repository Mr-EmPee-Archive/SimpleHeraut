package ml.empee.notifier.configs;

import java.util.Arrays;

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

    setComments("notifier", null);
    setComments("notifier.enabled", Arrays.asList(
        "It's recommended to leave enabled this service! (If you disable it you are going to miss even security updates)",
        "Before disabling it consider changing the report level"
    ));

    setComments("notifier.report-level",
        Arrays.asList(
            "The report-level declare which notification type you are going to receive.",
            "Possible values: UPDATE, BUG_FIX, IMPORTANT",
            "",
            "The UPDATE report level warns you when is available an update that",
            " add new content or improve performance",
            "",
            "The BUG_FIX report level warns you when is available an update that fixes a bug",
            "",
            "The IMPORTANT report level warns you when is available an update that",
            " is HIGHLY recommended to download"
        )
    );

  }
}
