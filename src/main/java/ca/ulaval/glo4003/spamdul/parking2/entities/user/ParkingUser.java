package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.InvalidAccess;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.BikePermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ParkingUser {

  private final Set<Car> cars = new HashSet<>();
  private final Set<Infraction> infractions = new HashSet<>();
  private Optional<BikePermit> bikePermit = Optional.empty();

  public void validateAccess(LicensePlate licensePlate, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    validateAccess(findCarBy(licensePlate), accessDateTime, parkingZone);
  }

  public void validateAccess(PermitNumber permitNumber, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    validateAccess(findCarBy(permitNumber), accessDateTime, parkingZone);
  }

  private void validateAccess(Optional<Car> car, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (!car.isPresent()) {
      throw new InvalidAccess();
    }

    car.get().validateAccess(accessDateTime, parkingZone);
  }

  public void setBikePermit(BikePermit bikePermit) {
    this.bikePermit = Optional.of(bikePermit);
  }

  public void addCar(Car car) {
    cars.add(car);
  }

  public void addInfraction(Infraction infraction) {
    infractions.add(infraction);
  }

  public boolean hasCarWith(LicensePlate licensePlate) {
    return findCarBy(licensePlate).isPresent();
  }

  public boolean hasCarWith(PermitNumber permitNumber) {
    return findCarBy(permitNumber).isPresent();
  }

  private Optional<Car> findCarBy(LicensePlate licensePlate) {
    return cars.stream().filter(car -> car.getLicensePlate().equals(licensePlate)).findFirst();
  }

  private Optional<Car> findCarBy(PermitNumber permitNumber) {
    return cars.stream().filter(car -> car.getPermit().isPresent() && car.getPermit()
                                                                         .get()
                                                                         .getPermitNumber()
                                                                         .equals(permitNumber)
    ).findFirst();
  }
}
