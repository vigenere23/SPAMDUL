package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public class InfractionNotFoundException extends RuntimeException {

  public InfractionNotFoundException() {
    super("There is no infraction with this id");
  }
}
