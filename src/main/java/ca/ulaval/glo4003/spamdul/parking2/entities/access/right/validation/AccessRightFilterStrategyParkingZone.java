package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccessRightFilterStrategyParkingZone implements AccessRightFilterStrategy {

  @Override
  public Set<AccessRight> filter(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime) {
    Set<AccessRight> validAccessRights = new HashSet<>();

    accessRights.forEach(accessRight -> {
      try {
        accessRight.validateAccess(parkingZone);
        validAccessRights.add(accessRight);
      } catch (InvalidParkingZoneException exception) {
        // DO NOTHING
      }
    });

    return validAccessRights;
  }

  @Override
  public void throwExceptionFor(ParkingZone parkingZone, LocalDateTime accessDateTime) throws InvalidAccessException {
    throw new InvalidParkingZoneException(parkingZone);
  }
}
