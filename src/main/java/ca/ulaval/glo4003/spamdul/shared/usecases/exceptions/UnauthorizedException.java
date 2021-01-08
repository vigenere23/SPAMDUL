package ca.ulaval.glo4003.spamdul.shared.usecases.exceptions;

public class UnauthorizedException extends UseCaseException {

  public UnauthorizedException(String name, String code, String message) {
    super(name, code, message);
  }

  public UnauthorizedException(RuntimeException exception) {
    super(exception);
  }
}
