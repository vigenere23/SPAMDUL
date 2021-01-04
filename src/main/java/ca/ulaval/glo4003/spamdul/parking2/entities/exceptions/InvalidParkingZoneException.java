package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;

public class InvalidParkingZoneException extends InvalidAccessException {

  public InvalidParkingZoneException(ParkingZone parkingZone) {
    super(String.format("Parking not permitted in zone %s", parkingZone));
  }
}
