package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidPermitException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CarPermit extends Permit {

  private final Set<AccessRight> accessRights = new HashSet<>();

  public CarPermit(PermitNumber permitNumber) {
    super(permitNumber);
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime) {
    if (accessRights.isEmpty()) {
      throw new InvalidPermitException(permitNumber);
    }

    accessRights.forEach(accessRight -> accessRight.validateAccess(accessDateTime));
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (accessRights.isEmpty()) {
      throw new InvalidPermitException(permitNumber);
    }

    accessRights.forEach(accessRight -> accessRight.validateAccess(accessDateTime, parkingZone));
  }

  public void addAccessRight(AccessRight accessRight) {
    this.accessRights.add(accessRight);
  }
}
