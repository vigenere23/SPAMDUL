package ca.ulaval.glo4003.spamdul.assemblers.parking.pass.exceptions;

public class InvalidParkingZoneException extends InvalidPassArgumentException {

  public String getError() {
    return "INVALID_PARKING_ZONE";
  }

  public String getDescription() {
    return "Invalid parking zone";
  }
}
