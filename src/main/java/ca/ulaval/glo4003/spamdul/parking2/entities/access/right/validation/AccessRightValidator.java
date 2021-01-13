package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccessRightValidator {

  private final List<AccessRightFilterStrategy> filters;

  public AccessRightValidator(List<AccessRightFilterStrategy> filters) {
    this.filters = filters;
  }

  public void validate(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime) {
    Set<AccessRight> filteredAccessRights = new HashSet<>(accessRights);

    for (AccessRightFilterStrategy filter : filters) {
      filteredAccessRights = filter.filter(filteredAccessRights, parkingZone, accessDateTime);

      if (accessRights.isEmpty()) {
        filter.throwExceptionFor(parkingZone, accessDateTime);
      }
    }
  }
}
