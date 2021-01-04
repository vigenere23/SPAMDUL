package ca.ulaval.glo4003.spamdul.parking2.entities.car;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import java.time.LocalDateTime;
import java.util.Optional;

public class Car {

  private final LicensePlate licensePlate;
  private Optional<CarPermit> permit = Optional.empty();
  private final CarType carType;

  public Car(LicensePlate licensePlate,
             CarType carType) {
    this.licensePlate = licensePlate;
    this.carType = carType;
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    if (!this.getPermit().isPresent()) {
      throw new InvalidAccessException();
    }

    this.getPermit().get().validateAccess(accessDateTime);
  }

  public void validateParking(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (!this.getPermit().isPresent()) {
      throw new InvalidAccessException();
    }

    this.getPermit().get().validateAccess(accessDateTime, parkingZone);
  }

  public void setPermit(CarPermit permit) {
    this.permit = Optional.of(permit);
  }

  public LicensePlate getLicensePlate() {
    return licensePlate;
  }

  public Optional<CarPermit> getPermit() {
    return permit;
  }
}
