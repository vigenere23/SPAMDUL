package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions;

public class InvalidParkingZoneException extends InvalidPassArgumentException {

  public InvalidParkingZoneException() {
    super("Invalid parking zone");
  }
}
