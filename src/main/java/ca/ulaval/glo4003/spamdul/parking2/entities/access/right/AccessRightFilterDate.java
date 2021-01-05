package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessDateException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccessRightFilterDate implements AccessRightFilter {

  @Override
  public Set<AccessRight> filter(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime) {
    Set<AccessRight> validAccessRights = new HashSet<>();

    accessRights.forEach(accessRight -> {
      try {
        accessRight.validateAccess(accessDateTime);
        validAccessRights.add(accessRight);
      } catch (InvalidAccessDateException exception) {
        // DO NOTHING
      }
    });

    if (validAccessRights.isEmpty()) {
      throw new InvalidAccessDateException(accessDateTime.toLocalDate());
    }

    return validAccessRights;
  }
}
