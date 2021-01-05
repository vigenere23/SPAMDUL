package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarMismatchException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ParkingUser {

  private final ParkingUserId parkingUserId;
  private final String name;
  private final Sex sex;
  private final LocalDate birthDate;

  private final Map<PermitNumber, Permit> permits = new HashMap<>();
  private final Set<Infraction> infractions = new HashSet<>();

  public ParkingUser(ParkingUserId parkingUserId,
                     String name,
                     Sex sex, LocalDate birthDate) {
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

  public void addPermit(Permit permit) {
    this.permits.put(permit.getPermitNumber(), permit);
  }

  public void addAccessRight(LicensePlate licensePlate, AccessRight accessRight) {
    CarPermit permit = findPermitBy(licensePlate);
    permit.addAccessRight(accessRight);
  }

  public void addInfraction(Infraction infraction) {
    infractions.add(infraction);
  }

  public ParkingUserId getId() {
    return parkingUserId;
  }

  private Permit findPermitBy(PermitNumber permitNumber) {
    return Optional.ofNullable(permits.get(permitNumber)).orElseThrow(() -> new PermitNotFoundException(permitNumber));
  }

  private CarPermit findPermitBy(LicensePlate licensePlate) {
    return permits.values().stream()
                  .filter(permit -> permit instanceof CarPermit)
                  .map(permit -> (CarPermit) permit)
                  .filter(
                      carPermit -> carPermit.hasCarWithLicensePlate(licensePlate))
                  .findFirst()
                  .orElseThrow(() -> new PermitNotFoundException(licensePlate));
  }

  public boolean hasPermitWith(PermitNumber permitNumber) {
    try {
      findPermitBy(permitNumber);
      return true;
    } catch (PermitNotFoundException exception) {
      return false;
    }
  }

  public boolean hasPermitWith(LicensePlate licensePlate) {
    try {
      findPermitBy(licensePlate);
      return true;
    } catch (PermitNotFoundException exception) {
      return false;
    }
  }
}
