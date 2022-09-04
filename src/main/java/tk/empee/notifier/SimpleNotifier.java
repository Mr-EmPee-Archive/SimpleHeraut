package tk.empee.notifier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.empee.notifier.configs.DefaultConfig;
import tk.empee.notifier.notifiers.DefaultNotifier;
import tk.empee.notifier.notifiers.Notifier;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class SimpleNotifier {

    private static SimpleNotifier instance;
    public static SimpleNotifier getInstance(String pluginID, JavaPlugin plugin) {
        if(instance == null) {
            instance = new SimpleNotifier(pluginID, plugin);
        }

        return instance;
    }

    public static void checkNotifications(String pluginID, JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, getInstance(pluginID, plugin)::checkNotifications);
    }


    private final JavaPlugin plugin;

    private final DefaultConfig config;
    private final String pluginID;
    private final Notifier notifier;

    private SimpleNotifier(String pluginID, JavaPlugin plugin) {
        this(pluginID, new DefaultNotifier(plugin.getLogger()), plugin);
    }
    private SimpleNotifier(String pluginID, Notifier notifier, JavaPlugin plugin) {
        this.config = new DefaultConfig(plugin);

        this.pluginID = pluginID;
        this.plugin = plugin;
        this.notifier = notifier;
    }

    public void checkNotifications() {
        if(!config.isEnabled()) {
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("https://minecraft-api.empee.workers.dev/notifications?"
                    + "plugin-id=" + pluginID
                    + "&plugin-version=" + plugin.getDescription().getVersion()
                    + "&report-level=" + config.getReportLevel().ordinal()
                )
        ).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            notifier.notify(response.body());
        } catch (IOException | InterruptedException e) {
            notifier.handleException(e);
        }

    }
}
