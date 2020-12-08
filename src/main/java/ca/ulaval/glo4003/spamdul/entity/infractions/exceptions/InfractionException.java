package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public abstract class InfractionException extends RuntimeException {
  //TODO:: add exception for all case?
  public InfractionException(String message) {
    super(message);
  }
}
