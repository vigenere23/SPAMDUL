package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarMismatchException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ParkingUser {

  private final ParkingUserId parkingUserId;
  private final String name;
  private final Sex sex;
  private final LocalDate birthDate;

  private final Set<Car> cars = new HashSet<>();
  private final Set<Infraction> infractions = new HashSet<>();
  private Optional<Permit> bikePermit = Optional.empty();

  public ParkingUser(ParkingUserId parkingUserId, String name, Sex sex, LocalDate birthDate) {
    this.parkingUserId = parkingUserId;
    this.name = name;
    this.sex = sex;
    this.birthDate = birthDate;
  }

  public void validateAccess(LicensePlate licensePlate, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    findPermitBy(licensePlate).validateAccess(accessDateTime, parkingZone);
  }

  public void validateAccess(PermitNumber permitNumber, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    findPermitBy(permitNumber).validateAccess(accessDateTime, parkingZone);
  }

  public void validateAccess(PermitNumber permitNumber,
                             LicensePlate licensePlate,
                             LocalDateTime accessDateTime,
                             ParkingZone parkingZone) {
    Permit permit = findPermitBy(permitNumber);
    if (!permit.equals(findPermitBy(licensePlate))) {
      throw new CarMismatchException(permitNumber, licensePlate);
    }

    permit.validateAccess(accessDateTime, parkingZone);
  }

  public void setBikePermit(Permit bikePermit) {
    this.bikePermit = Optional.of(bikePermit);
  }

  public void addCar(Car car) {
    cars.add(car);
  }

  public void addInfraction(Infraction infraction) {
    infractions.add(infraction);
  }

  public boolean hasPermitWith(LicensePlate licensePlate) {
    try {
      findPermitBy(licensePlate);
      return true;
    } catch (PermitNotFoundException | CarNotFoundException exception) {
      return false;
    }
  }

  public boolean hasPermitWith(PermitNumber permitNumber) {
    try {
      findPermitBy(permitNumber);
      return true;
    } catch (PermitNotFoundException | CarNotFoundException exception) {
      return false;
    }
  }

  public ParkingUserId getId() {
    return parkingUserId;
  }

  private Permit findPermitBy(LicensePlate licensePlate) {
    return findCarBy(licensePlate).getPermit().orElseThrow(() -> new PermitNotFoundException(licensePlate));
  }

  private Permit findPermitBy(PermitNumber permitNumber) {
    if (bikePermit.isPresent() && bikePermit.get().getPermitNumber().equals(permitNumber)) {
      return bikePermit.get();
    }

    return findCarBy(permitNumber).getPermit().orElseThrow(() -> new PermitNotFoundException(permitNumber));
  }

  private Car findCarBy(LicensePlate licensePlate) {
    return cars
        .stream()
        .filter(car -> car.getLicensePlate().equals(licensePlate))
        .findFirst()
        .orElseThrow(() -> new CarNotFoundException(licensePlate));
  }

  private Car findCarBy(PermitNumber permitNumber) {
    return cars
        .stream()
        .filter(car -> car.getPermit().isPresent() && car.getPermit()
                                                         .get()
                                                         .getPermitNumber()
                                                         .equals(permitNumber))
        .findFirst()
        .orElseThrow(() -> new CarNotFoundException(permitNumber));
  }
}
