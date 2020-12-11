package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions;

public abstract class InvalidInfractionException extends RuntimeException {

  protected InvalidInfractionException(String message) {
    super(message);
  }
}
