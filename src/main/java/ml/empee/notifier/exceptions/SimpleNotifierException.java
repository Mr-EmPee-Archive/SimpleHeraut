package ml.empee.notifier.exceptions;

public class SimpleNotifierException extends RuntimeException {

  public SimpleNotifierException(String message) {
    super(message);
  }

  public SimpleNotifierException(String message, Throwable cause) {
    super(message, cause);
  }

}
