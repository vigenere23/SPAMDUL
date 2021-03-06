package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.accessright.AccessRightCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import java.util.List;
import java.util.stream.Collectors;

public class AccessRightCreationAssembler {

  private final AccessPeriodCreationAssembler accessPeriodCreationAssembler;

  public AccessRightCreationAssembler(AccessPeriodCreationAssembler accessPeriodCreationAssembler) {
    this.accessPeriodCreationAssembler = accessPeriodCreationAssembler;
  }

  public List<AccessRightCreationDto> fromRequests(List<AccessRightCreationRequest> requests) {
    return requests.stream().map(this::fromRequest).collect(Collectors.toList());
  }

  public AccessRightCreationDto fromRequest(AccessRightCreationRequest request) {
    AccessRightCreationDto dto = new AccessRightCreationDto();

    dto.parkingZone = ParkingZone.parse(request.parkingZone.toUpperCase());
    dto.period = accessPeriodCreationAssembler.fromRequest(request.period);

    return dto;
  }
}
