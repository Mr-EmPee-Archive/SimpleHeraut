package ml.empee.notifier.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.ConfigFile;
import ml.empee.configurator.annotations.Path;
import ml.empee.notifier.ReportLevel;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

@SuppressWarnings("unused")
@Getter @Setter(AccessLevel.PRIVATE)
public final class DefaultConfig extends ConfigFile {

    public DefaultConfig(JavaPlugin plugin) {
        super(plugin, "notifier.yml", false);
    }

    @Path(value = "notifier.enabled", required = true)
    private Boolean enabled;

    @Path("notifier.report-level")
    private ReportLevel reportLevel;

    private void setReportLevel(String level) {
        this.reportLevel = ReportLevel.valueOf(level.toUpperCase(Locale.ROOT));
    }

}
