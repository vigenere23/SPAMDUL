package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import java.time.LocalDateTime;
import java.util.Set;

public interface AccessRightFilter {

  Set<AccessRight> filter(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime);
}
