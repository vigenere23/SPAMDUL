package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccessRightFilterParkingZone implements AccessRightFilter {

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

    if (validAccessRights.isEmpty()) {
      throw new InvalidParkingZoneException(parkingZone);
    }

    return validAccessRights;
  }
}
