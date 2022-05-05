package tk.empee.notifier.configs;

import com.electronwill.nightconfig.core.conversion.Conversion;
import com.electronwill.nightconfig.core.conversion.Converter;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.SpecStringInArray;
import com.electronwill.nightconfig.core.utils.minecraft.MinecraftManualConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tk.empee.notifier.ReportLevel;

import java.util.Locale;

public final class DefaultConfig extends MinecraftManualConfig {

    protected DefaultConfig(JavaPlugin plugin, String path, boolean copyResource) {
        super(plugin, path, copyResource);
    }

    @Path("notifier.enabled")
    @Getter private boolean enabled;

    @Conversion(ReportLevelConverter.class)
    @SpecStringInArray({"INFO", "UPDATE", "BUG_FIX"})
    @Path("notifier.report-level")
    @Getter private ReportLevel reportLevel;

    private static class ReportLevelConverter implements Converter<ReportLevel, String> {
        @Override
        public ReportLevel convertToField(String value) {
            return ReportLevel.valueOf(value.toUpperCase(Locale.ENGLISH));
        }

        @Override
        public String convertFromField(ReportLevel value) {
            return value.name();
        }
    }

}
