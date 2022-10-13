package ml.empee.notifier;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ml.empee.configurator.ConfigurationManager;
import ml.empee.notifier.configs.NotifierConfig;
import ml.empee.notifier.notifiers.DefaultNotifier;
import ml.empee.notifier.notifiers.Notifier;

public final class SimpleNotifier implements Listener {

  private final JavaPlugin plugin;

  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  private final NotifierConfig config;
  private final String url;
  private final Notifier notifier;

  public static void scheduleNotifier(String pluginID, Notifier notifier, JavaPlugin plugin, Long hours) {
    SimpleNotifier instance = new SimpleNotifier(pluginID, notifier, plugin);
    Bukkit.getScheduler().runTaskTimerAsynchronously(
        plugin, instance::checkNotifications,
        0, 20 * 60 * 60 * hours
    );
  }
  public static void scheduleNotifier(String pluginID, JavaPlugin plugin, Long hours) {
    scheduleNotifier(pluginID, new DefaultNotifier(plugin.getLogger()), plugin, hours);
  }

  public SimpleNotifier(String pluginID, JavaPlugin plugin) {
    this(pluginID, new DefaultNotifier(plugin.getLogger()), plugin);
  }
  public SimpleNotifier(String pluginID, Notifier notifier, JavaPlugin plugin) {
    this.config = ConfigurationManager.loadConfiguration(new NotifierConfig(plugin));

    this.plugin = plugin;
    this.notifier = notifier;
    this.url = String.format(
        "https://minecraft-api.empee.workers.dev/notifications?plugin-id=%s&plugin-version=%s&report-level=%s",
        pluginID, plugin.getDescription().getVersion(), config.getReportLevel().ordinal()
    );
  }

  public void checkNotifications() {
    if (Boolean.FALSE.equals(config.getEnabled())) {
      return;
    }

    HttpGet request = new HttpGet(url);
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        notifier.notify(EntityUtils.toString(entity));
      }

    } catch (IOException e) {
      notifier.handleException(e);
    }

  }

  @EventHandler
  public void onServerStop(PluginDisableEvent event) {
    if (event.getPlugin().equals(plugin)) {
      try {
        httpClient.close();
      } catch (IOException e) {
        notifier.handleException(e);
      }
    }
  }

}
