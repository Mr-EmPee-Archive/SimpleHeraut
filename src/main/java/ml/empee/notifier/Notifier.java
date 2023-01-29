package ml.empee.notifier;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ml.empee.notifier.configs.NotifierConfig;
import ml.empee.notifier.model.Update;
import ml.empee.notifier.utils.GsonUtils;
import ml.empee.notifier.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Notifier {

  private final NotifierConfig config;
  private final JavaPlugin plugin;
  private final Logger log;
  private final URL fetchUpdateURL;


  public static Notifier listenForUpdates(JavaPlugin plugin, String resourceID) {
    try {
      Notifier notifier = new Notifier(plugin, resourceID);
      if(notifier.config.getEnabled()) {
        notifier.scheduleUpdateChecking();
      }

      return notifier;
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Invalid resource ID: " + resourceID);
    }
  }
  public Notifier(JavaPlugin plugin, String resourceID) throws MalformedURLException {
    this.plugin = plugin;
    this.config = new NotifierConfig(plugin);
    this.log = plugin.getLogger();

    this.fetchUpdateURL = new URL(
        "https://api.spiget.org/v2/resources/" + resourceID + "/updates?sort=-title&fields=title%2Cdescription"
    );
  }

  private void scheduleUpdateChecking() {
    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
      try {
        checkForUpdates();
      } catch (IOException e) {
        log.info("Failed to check for updates: " + e.getMessage());
      }
    }, 0, TimeUnit.HOURS.toSeconds(2) * 20);
  }

  public void checkForUpdates() throws IOException {
    log.info("Checking for updates...");
    List<Update> updates = getUpdates();

    if(updates.size() == 0) {
      log.info("No updates found.");
    } else {
        log.warning("Found " + updates.size() + " updates\n");
        for(Update update : updates) {
            log.warning("\n" + update + "\n");
        }
    }
  }

  private List<Update> getUpdates() throws IOException {
    HttpURLConnection connection = fetchUpdates();
    List<Update> updates = Stream.of(GsonUtils.parseUpdates(connection.getInputStream()))
        .filter(u -> u.getPriority().ordinal() >= config.getReportLevel().ordinal())
        .filter(u -> VersionUtils.compareVersions(u.getVersion(), plugin.getDescription().getVersion()) > 0)
        .collect(Collectors.toList());
    connection.disconnect();
    return updates;
  }

  private HttpURLConnection fetchUpdates() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) fetchUpdateURL.openConnection();
    connection.setRequestMethod("GET");

    if(connection.getResponseCode() > 299) {
        throw new IOException("Failed to fetch updates: " + connection.getResponseCode());
    }

    return connection;
  }

}
