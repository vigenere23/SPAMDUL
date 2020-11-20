package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationException;

public class NoRegisteredUserLoggedInException extends AuthenticationException {

  public NoRegisteredUserLoggedInException() {
    super("no registered user logged in with this token");
  }
}
