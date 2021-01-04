package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

public class InfractionTypeNotFoundException extends RuntimeException {

  public InfractionTypeNotFoundException(InvalidAccessException exception) {
    super(String.format("No infraction type found for %s", exception.getClass().getName()));
  }
}
