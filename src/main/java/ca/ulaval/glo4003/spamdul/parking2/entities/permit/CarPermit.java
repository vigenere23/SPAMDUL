package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidPermitException;
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
  private final Car car;
  private final Set<AccessRight> accessRights = new HashSet<>();

  public CarPermit(PermitNumber permitNumber, Car car) {
    super(permitNumber);
    this.car = car;
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (accessRights.isEmpty()) {
      throw new InvalidPermitException(permitNumber);
    }

    if (!PERMITTED_ZONES.contains(parkingZone)) {
      throw new InvalidParkingZoneException(parkingZone);
    }

    // TODO this is not working
    // we need to find 1 valid access right, else we throw an exception
    // could create an access right filter
    // each access right can then be validated by parking zone and accessDateTime individually
    accessRights.forEach(accessRight -> accessRight.validateAccess(accessDateTime, parkingZone));
  }

  public boolean hasCarWithLicensePlate(LicensePlate licensePlate) {
    return car.getLicensePlate().equals(licensePlate);
  }

  public void addAccessRight(AccessRight accessRight) {
    this.accessRights.add(accessRight);
  }
}
