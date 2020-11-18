package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception;

public class WrongCredentialsException extends RuntimeException {

  public WrongCredentialsException() {
    super("Wrong credentials provided");
  }
}
