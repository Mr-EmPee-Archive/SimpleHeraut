package ml.empee.notifier.notifiers;

public interface Notifier {

  void notify(String changelog);

  void handleException(Exception exception);

}
