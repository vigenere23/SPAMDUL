package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions;

public abstract class InvalidCarArgumentException extends RuntimeException {

  protected InvalidCarArgumentException(String message) {
    super(message);
  }
}
