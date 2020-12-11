package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

public class CantCreatePassCodeException extends RuntimeException {

  public CantCreatePassCodeException() {
    super("Cant create a parking pass code directly");
  }
}
