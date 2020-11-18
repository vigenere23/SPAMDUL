package ca.ulaval.glo4003.spamdul.entity.authentication;

public class NoRegisteredUserLoggedInException extends RuntimeException {

  public NoRegisteredUserLoggedInException() {
    super("no registered user logged in with this token");
  }
}
