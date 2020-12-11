package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {

  public InvalidCredentialsException() {
    super("Invalid credentials provided");
  }
}
