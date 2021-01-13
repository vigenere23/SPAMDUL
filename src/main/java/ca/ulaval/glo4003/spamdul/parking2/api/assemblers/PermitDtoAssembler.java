package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.PermitResponse;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitDto;
import java.util.Set;
import java.util.stream.Collectors;

public class PermitDtoAssembler {

  private final CarDtoAssembler carDtoAssembler;
  private final AccessRightDtoAssembler accessRightDtoAssembler;

  public PermitDtoAssembler(CarDtoAssembler carDtoAssembler,
                            AccessRightDtoAssembler accessRightDtoAssembler) {
    this.carDtoAssembler = carDtoAssembler;
    this.accessRightDtoAssembler = accessRightDtoAssembler;
  }

  public Set<PermitResponse> toResponses(Set<PermitDto> dtos) {
    return dtos.stream().map(this::toResponse).collect(Collectors.toSet());
  }

  public PermitResponse toResponse(PermitDto dto) {
    PermitResponse response = new PermitResponse();
    response.number = dto.number.toString();
    response.type = dto.type;

    if (dto.car != null) {
      response.car = carDtoAssembler.toResponse(dto.car);
    }

    if (dto.accessRights != null) {
      response.accessRights = accessRightDtoAssembler.toResponses(dto.accessRights);
    }

    return response;
  }
}
