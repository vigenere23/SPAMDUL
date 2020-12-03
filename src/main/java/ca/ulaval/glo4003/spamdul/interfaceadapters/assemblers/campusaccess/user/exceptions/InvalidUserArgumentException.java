package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions;

public abstract class InvalidUserArgumentException extends RuntimeException {

  protected InvalidUserArgumentException(String message) {
    super(message);
  }

}
