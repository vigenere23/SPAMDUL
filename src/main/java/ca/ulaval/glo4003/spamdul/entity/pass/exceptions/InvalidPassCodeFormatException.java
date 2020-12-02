package ca.ulaval.glo4003.spamdul.entity.pass.exceptions;

public class InvalidPassCodeFormatException extends RuntimeException {

  public InvalidPassCodeFormatException(String message) {
    super(message);
  }
}
