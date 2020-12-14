package ca.ulaval.glo4003.spamdul.assemblers.infraction.exceptions;

public class InvalidInfractionParkingZoneException extends InvalidInfractionException {

  public String getError() {
    return "INVALID_PARKING_ZONE";
  }

  public String getDescription() {
    return "Invalid parking zone";
  }
}
