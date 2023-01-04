package ml.empee.notifier;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NotifierTest {

  @Test
  void testNotification() throws IOException {
    JavaPlugin plugin = Mockito.mock(JavaPlugin.class);
    when(plugin.getLogger()).thenReturn(Logger.getLogger("MockedPlugin"));
    when(plugin.getDataFolder()).thenReturn(new File("src/test/resources"));
    PluginDescriptionFile description = Mockito.mock(PluginDescriptionFile.class);
    when(description.getVersion()).thenReturn("1.5.0");
    when(plugin.getDescription()).thenReturn(description);
    Mockito.mockStatic(JavaPlugin.class).when(() -> JavaPlugin.getProvidingPlugin(any())).thenReturn(
        plugin
    );

    Notifier notifier = new Notifier(plugin, "105671");
    notifier.checkForUpdates();
  }

}
