package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import java.time.LocalDateTime;

public class AccessRight {

  private final ParkingZone parkingZone;
  private final AccessPeriod accessPeriod;

  public AccessRight(ParkingZone parkingZone, AccessPeriod accessPeriod) {
    this.parkingZone = parkingZone;
    this.accessPeriod = accessPeriod;
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    accessPeriod.validateAccess(accessDateTime);
  }

  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (this.parkingZone.compareTo((parkingZone)) < 0) {
      throw new InvalidParkingZoneException(parkingZone);
    }

    accessPeriod.validateAccess(accessDateTime);
  }
}
