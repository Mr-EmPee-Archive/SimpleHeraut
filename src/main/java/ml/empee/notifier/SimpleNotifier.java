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

import ml.empee.notifier.configs.DefaultConfig;
import ml.empee.notifier.notifiers.DefaultNotifier;
import ml.empee.notifier.notifiers.Notifier;

public final class SimpleNotifier implements Listener {

  private static SimpleNotifier instance;
  private final JavaPlugin plugin;

  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  private final DefaultConfig config;
  private final String url;
  private final Notifier notifier;

  public static SimpleNotifier getInstance(String pluginID, JavaPlugin plugin) {
    if (instance == null) {
      instance = new SimpleNotifier(pluginID, plugin);
      plugin.getServer().getPluginManager().registerEvents(instance, plugin);
    }

    return instance;
  }

  public static void checkNotifications(String pluginID, JavaPlugin plugin) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, getInstance(pluginID, plugin)::checkNotifications);
  }

  private SimpleNotifier(String pluginID, JavaPlugin plugin) {
    this(pluginID, new DefaultNotifier(plugin.getLogger()), plugin);
  }

  private SimpleNotifier(String pluginID, Notifier notifier, JavaPlugin plugin) {
    this.config = DefaultConfig.getInstance(plugin);

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
