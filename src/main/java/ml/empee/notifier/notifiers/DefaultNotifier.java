package ml.empee.notifier.notifiers;

import java.util.logging.Level;
import java.util.logging.Logger;

import ml.empee.notifier.exceptions.SimpleNotifierException;

public final class DefaultNotifier implements Notifier {

  private final Logger logger;

  public DefaultNotifier(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void notify(String changelog) {
    if(!changelog.isEmpty()) {
      changelog = "Your plugin is outdated! Please update it to the latest version\n" +
                  "Info on what's changed: \n" + changelog;
      String[] messages = changelog.split("\n");
      String separator = buildSeparator(messages);

      logger.warning(separator);
      for(String message : messages) {
        logger.log(Level.WARNING, "* {0}", message);
      }
      logger.warning(separator);
    }
  }

  private String buildSeparator(String[] lines) {
    StringBuilder separator = new StringBuilder();

    int length = findMostLength(lines);
    for(int i=0; i<length; i++) {
      separator.append("*");
    }

    return separator.toString();
  }

  private int findMostLength(String[] lines) {
    int maxLength = 0;
    for(String line : lines) {
      if(line.length() > maxLength) {
        maxLength = line.length();
      }
    }
    return maxLength;
  }

  @Override
  public void handleException(Exception exception) {
    throw new SimpleNotifierException("Error while checking for notifications", exception);
  }

}
