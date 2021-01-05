package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AccessRightValidator {

  private final List<AccessRightFilter> filters;

  public AccessRightValidator(List<AccessRightFilter> filters) {
    this.filters = filters;
  }

  public void validate(Set<AccessRight> accessRights, ParkingZone parkingZone, LocalDateTime accessDateTime) {
    Set<AccessRight> filteredAccessRights = accessRights;

    for (AccessRightFilter filter : filters) {
      filteredAccessRights = filter.filter(filteredAccessRights, parkingZone, accessDateTime);
    }

    // TODO recheck if filteredAccessRightIsEmpty
  }
}
