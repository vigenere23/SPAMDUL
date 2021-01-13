package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import java.time.LocalDateTime;
import java.util.Set;

public interface AccessRightFilterStrategy {

  Set<AccessRight> filter(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime);

  void throwExceptionFor(ParkingZone parkingZone, LocalDateTime accessDateTime) throws InvalidAccessException;
}
