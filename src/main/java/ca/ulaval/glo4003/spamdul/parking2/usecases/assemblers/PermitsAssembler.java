package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.BikePermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitDto;
import java.util.Set;
import java.util.stream.Collectors;

public class PermitsAssembler {

  private final AccessRightsAssembler accessRightsAssembler;
  private final CarAssembler carAssembler;

  public PermitsAssembler(AccessRightsAssembler accessRightsAssembler,
                          CarAssembler carAssembler) {
    this.accessRightsAssembler = accessRightsAssembler;
    this.carAssembler = carAssembler;
  }

  public Set<PermitDto> toDtos(Set<Permit> permits) {
    return permits.stream().map(this::toDto).collect(Collectors.toSet());
  }

  public PermitDto toDto(Permit permit) {
    PermitDto dto = new PermitDto();
    dto.number = permit.getPermitNumber();

    if (permit instanceof CarPermit) {
      CarPermit carPermit = (CarPermit) permit;
      dto.type = "car";
      dto.accessRights = accessRightsAssembler.toDtos(carPermit.getAccessRights());
      dto.car = carAssembler.toDto(carPermit.getCar());
    } else if (permit instanceof BikePermit) {
      dto.type = "bike";
    }

    return dto;
  }
}
