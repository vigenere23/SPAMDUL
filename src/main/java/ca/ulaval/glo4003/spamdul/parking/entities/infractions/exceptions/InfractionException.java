package ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions;

public abstract class InfractionException extends RuntimeException {
  public InfractionException(String message) {
    super(message);
  }
}
