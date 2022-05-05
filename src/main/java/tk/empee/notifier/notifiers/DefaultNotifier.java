package tk.empee.notifier.notifiers;

import java.util.logging.Logger;

public final class DefaultNotifier implements Notifier {
    
    private final Logger logger;
    public DefaultNotifier(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void notify(String changelog) {
        logger.info(changelog);
    }

    @Override
    public void handleException(Exception exception) {
        throw new RuntimeException("Error while checking for notifications", exception);
    }

}
