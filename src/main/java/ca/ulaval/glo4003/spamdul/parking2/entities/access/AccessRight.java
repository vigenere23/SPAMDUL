package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import java.time.LocalDateTime;

public class AccessRight {

  private final ParkingZone parkingZone;
  private final AccessPeriod accessPeriod;

  public AccessRight(ParkingZone parkingZone, AccessPeriod accessPeriod) {
    this.parkingZone = parkingZone;
    this.accessPeriod = accessPeriod;
  }

  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (this.parkingZone.compareTo((parkingZone)) < 0) {
      throw new InvalidAccess();
    }

    accessPeriod.validateAccess(accessDateTime);
  }
}
