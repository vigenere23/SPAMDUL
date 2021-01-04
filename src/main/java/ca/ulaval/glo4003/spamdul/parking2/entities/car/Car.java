package ca.ulaval.glo4003.spamdul.parking2.entities.car;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import java.time.LocalDateTime;
import java.util.Optional;

public class Car {

  private final String brand;
  private final String model;
  private final int year;
  private final CarType carType;
  private final LicensePlate licensePlate;
  private Optional<Permit> permit = Optional.empty();

  public Car(String brand, String model, int year, LicensePlate licensePlate,
             CarType carType) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.licensePlate = licensePlate;
    this.carType = carType;
  }

  // TODO this needs to be called for usage stats...
  public void validateParking(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (!this.getPermit().isPresent()) {
      throw new PermitNotFoundException(licensePlate);
    }

    this.getPermit().get().validateAccess(accessDateTime, parkingZone);
  }

  public void setPermit(Permit permit) {
    this.permit = Optional.of(permit);
  }

  public LicensePlate getLicensePlate() {
    return licensePlate;
  }

  public Optional<Permit> getPermit() {
    return permit;
  }
}
