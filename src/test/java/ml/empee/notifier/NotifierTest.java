package ml.empee.notifier;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class NotifierTest {

  @Test
  void testNotification() {

    Server server = Mockito.mock(Server.class);
    PluginManager pluginManager = Mockito.mock(PluginManager.class);
    JavaPlugin plugin = Mockito.mock(JavaPlugin.class);
    when(plugin.getLogger()).thenReturn(Logger.getLogger("MockedPlugin"));
    when(plugin.getDataFolder()).thenReturn(new File("src/test/resources"));
    when(plugin.getServer()).thenReturn(server);
    when(server.getPluginManager()).thenReturn(pluginManager);
    PluginDescriptionFile description = Mockito.mock(PluginDescriptionFile.class);
    when(description.getVersion()).thenReturn("1.0.0");
    when(plugin.getDescription()).thenReturn(description);

    SimpleNotifier notifier = new SimpleNotifier("13932", plugin);
    notifier.checkNotifications();

  }

}
