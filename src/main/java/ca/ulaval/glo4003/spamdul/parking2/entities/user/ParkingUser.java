package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarMismatchException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ParkingUser {

  private final AccountId accountId;
  private final String name;
  private final Sex sex;
  private final LocalDate birthDate;

  private final Map<PermitNumber, Permit> permits = new HashMap<>();
  private final Set<Infraction> infractions = new HashSet<>();

  public ParkingUser(AccountId accountId,
                     String name,
                     Sex sex, LocalDate birthDate) {
    this.accountId = accountId;
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

  public Amount getAccessRightPrice(ParkingZoneFeeRepository zoneFeeRepository,
                                    ParkingCarFeeRepository carFeeRepository,
                                    LicensePlate licensePlate,
                                    AccessRight accessRight) {
    return findPermitBy(licensePlate).getAccessRightPrice(carFeeRepository, zoneFeeRepository, accessRight);
  }

  public void addInfraction(Infraction infraction) {
    infractions.add(infraction);
  }

  public AccountId getAccountId() {
    return accountId;
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

  public String getName() {
    return name;
  }

  public Set<Permit> getPermits() {
    return new HashSet<>(permits.values());
  }
}
