package ml.empee.notifier;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotifierTest {
    
    @Test
    void testNotification() {

        JavaPlugin plugin = Mockito.mock(JavaPlugin.class);
        when(plugin.getLogger()).thenReturn(Logger.getLogger("MockedPlugin"));
        when(plugin.getDataFolder()).thenReturn( new File("src/test/resources") );
        PluginDescriptionFile description = Mockito.mock(PluginDescriptionFile.class);
        when(description.getVersion()).thenReturn("1.0.0");
        when(plugin.getDescription()).thenReturn(description);

        SimpleNotifier notifier = SimpleNotifier.getInstance("13932", plugin);
        notifier.checkNotifications();

    }

}
