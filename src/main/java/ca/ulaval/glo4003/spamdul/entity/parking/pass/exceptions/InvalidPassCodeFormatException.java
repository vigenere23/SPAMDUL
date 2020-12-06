package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

public class InvalidPassCodeFormatException extends RuntimeException {
  public InvalidPassCodeFormatException() {
    super("Invalid pass code format");
  }
}
