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
    dto.periodStart = accessRight.getTimePeriod().getPeriodStart();
    dto.periodEnd = accessRight.getTimePeriod().getPeriodEnd();
    dto.startTime = accessRight.getTimePeriod().getStartTime();
    dto.endTime = accessRight.getTimePeriod().getEndTime();

    if (accessRight.getTimePeriod().getDayOfWeek().isPresent()) {
      dto.dayOfWeek = accessRight.getTimePeriod().getDayOfWeek().get();
    }

    return dto;
  }
}
