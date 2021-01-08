package ca.ulaval.glo4003.spamdul.shared.usecases.exceptions;

public class UnhandledException extends UseCaseException {

  public UnhandledException(Exception exception) {
    super(exception);
  }
}
