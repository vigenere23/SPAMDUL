package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.accessright.AccessRightCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;

public class AccessRightCreationAssembler {

  private final AccessPeriodCreationAssembler accessPeriodCreationAssembler;

  public AccessRightCreationAssembler(AccessPeriodCreationAssembler accessPeriodCreationAssembler) {
    this.accessPeriodCreationAssembler = accessPeriodCreationAssembler;
  }

  public AccessRightCreationDto fromRequest(AccessRightCreationRequest request) {
    AccessRightCreationDto dto = new AccessRightCreationDto();

    dto.parkingZone = ParkingZone.valueOf(request.parkingZone.toUpperCase());
    dto.period = accessPeriodCreationAssembler.fromRequest(request.period);

    return dto;
  }
}
