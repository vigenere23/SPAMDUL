package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationException;

public class UnauthorizedUserException extends AuthenticationException {

  public UnauthorizedUserException() {
    super("Unauthorized user");
  }

}
