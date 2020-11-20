package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication;

public abstract class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }
}
