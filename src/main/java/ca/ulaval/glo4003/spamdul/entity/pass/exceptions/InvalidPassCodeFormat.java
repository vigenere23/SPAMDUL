package ca.ulaval.glo4003.spamdul.entity.pass.exceptions;

public class InvalidPassCodeFormat extends RuntimeException {

  public InvalidPassCodeFormat(String message) {
    super(message);
  }
}
