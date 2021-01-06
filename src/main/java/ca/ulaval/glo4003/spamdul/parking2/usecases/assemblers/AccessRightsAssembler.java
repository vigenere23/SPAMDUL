package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightDto;
import java.util.Set;
import java.util.stream.Collectors;

public class AccessRightsAssembler {

  public Set<AccessRightDto> toDtos(Set<AccessRight> accessRights) {
    return accessRights.stream().map(this::toDto).collect(Collectors.toSet());
  }

  public AccessRightDto toDto(AccessRight accessRight) {
    AccessRightDto dto = new AccessRightDto();
    dto.parkingZone = accessRight.getParkingZone();
    dto.periodStart = accessRight.getAccessPeriod().getPeriodStart();
    dto.periodEnd = accessRight.getAccessPeriod().getPeriodEnd();
    dto.startTime = accessRight.getAccessPeriod().getStartTime();
    dto.endTime = accessRight.getAccessPeriod().getEndTime();

    if (accessRight.getAccessPeriod().getDayOfWeek().isPresent()) {
      dto.dayOfWeek = accessRight.getAccessPeriod().getDayOfWeek().get();
    }

    return dto;
  }
}
