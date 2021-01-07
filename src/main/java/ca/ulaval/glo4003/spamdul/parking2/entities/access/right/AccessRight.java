package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public class AccessRight {

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

  public Amount getPrice(ParkingCarFeeRepository carFeeRepository,
                         ParkingZoneFeeRepository zoneFeeRepository,
                         CarType carType) {
    Amount zonePrice = accessPeriod.getZonePrice(zoneFeeRepository, parkingZone);
    Amount carPrice = accessPeriod.getCarTypePrice(carFeeRepository, carType);
    return zonePrice.add(carPrice);
  }

  @Override
  public String toString() {
    return String.format("Access right for zone %s with period of %s", parkingZone, accessPeriod);
  }

  public AccessPeriod getAccessPeriod() {
    return accessPeriod;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }
}
