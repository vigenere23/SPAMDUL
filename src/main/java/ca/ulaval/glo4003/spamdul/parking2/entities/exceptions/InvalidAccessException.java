package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

public abstract class InvalidAccessException extends RuntimeException {

  protected InvalidAccessException(String message) {
    super(message);
  }
}
