package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.Priceable;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public class AccessRight implements Priceable {

  private final ParkingZone parkingZone;
  private final AccessPeriod accessPeriod;

  public AccessRight(ParkingZone parkingZone, AccessPeriod accessPeriod) {
    this.parkingZone = parkingZone;
    this.accessPeriod = accessPeriod;
  }

  public void validateAccess(ParkingZone parkingZone) {
    if (this.parkingZone.compareTo((parkingZone)) < 0) {
      throw new InvalidParkingZoneException(parkingZone);
    }
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    accessPeriod.validateAccess(accessDateTime);
  }

  @Override
  public Amount getPrice() {
    // TODO use price calculator
    // How to access Car? Maybe use another object for that
    return Amount.valueOf(20.23);
  }

  @Override
  public String getName() {
    return String.format("Access right for zone %s", parkingZone); // TODO add period infos
  }

  public AccessPeriod getAccessPeriod() {
    return accessPeriod;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }
}
