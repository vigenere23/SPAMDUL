package ca.ulaval.glo4003.spamdul.shared.usecases.exceptions;

public class InvalidArgumentException extends UseCaseException {

  public InvalidArgumentException(RuntimeException exception) {
    super(exception);
  }
}
