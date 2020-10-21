package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public class InfractionNotFoundException extends RuntimeException {

  public InfractionNotFoundException(String message) {
    super(message);
  }
}
