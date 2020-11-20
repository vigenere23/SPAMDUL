package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationException;

public class WrongCredentialsException extends AuthenticationException {

  public WrongCredentialsException() {
    super("Wrong credentials provided");
  }
}
