package ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions;

public class WrongDayInfractionException extends InfractionException{
  public WrongDayInfractionException() {
    super("VIG_01");
  }
}
