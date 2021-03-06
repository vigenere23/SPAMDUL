package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightValidator;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidPermitException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarPermit extends Permit {

  private static final List<ParkingZone> PERMITTED_ZONES = ListUtil.toList(ParkingZone.ZONE_1,
                                                                           ParkingZone.ZONE_2,
                                                                           ParkingZone.ZONE_3,
                                                                           ParkingZone.ZONE_R,
                                                                           ParkingZone.ANY);
  private final AccessRightValidator accessRightValidator;
  private final Car car;
  private final Set<AccessRight> accessRights = new HashSet<>();

  public CarPermit(PermitNumber permitNumber, AccessRightValidator accessRightValidator, Car car) {
    super(permitNumber);
    this.accessRightValidator = accessRightValidator;
    this.car = car;
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (parkingZone == null) {
      throw new IllegalArgumentException("a parking zone must be specified");
    }

    if (accessDateTime == null) {
      throw new IllegalArgumentException("an access datetime must be specified");
    }

    if (accessRights.isEmpty()) {
      throw new InvalidPermitException(permitNumber);
    }

    if (!PERMITTED_ZONES.contains(parkingZone)) {
      throw new InvalidParkingZoneException(parkingZone);
    }

    accessRightValidator.validate(accessRights, parkingZone, accessDateTime);
  }

  public boolean hasCarWithLicensePlate(LicensePlate licensePlate) {
    return car.getLicensePlate().equals(licensePlate);
  }

  public void addAccessRight(AccessRight accessRight) {
    this.accessRights.add(accessRight);
  }

  public Set<AccessRight> getAccessRights() {
    return new HashSet<>(accessRights);
  }

  public Car getCar() {
    return car;
  }

  public Amount getAccessRightPrice(ParkingCarFeeRepository carFeeRepository,
                                    ParkingZoneFeeRepository zoneFeeRepository,
                                    AccessRight accessRight) {
    return accessRight.getPrice(carFeeRepository, zoneFeeRepository, car.getType());
  }

  @Override
  public Amount getPrice() {
    return Amount.valueOf(0);
  }

  @Override
  public String toString() {
    return String.format("Car permit with number %s", permitNumber);
  }
}
