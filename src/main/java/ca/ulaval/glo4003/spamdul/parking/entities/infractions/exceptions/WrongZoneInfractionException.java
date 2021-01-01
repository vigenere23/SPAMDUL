package ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions;

public class WrongZoneInfractionException extends InfractionException{

  public WrongZoneInfractionException() {
    super("ZONE_01");
  }
}
