package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingUserResponse;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;

public class ParkingUserDtoAssembler {

  private final PermitDtoAssembler permitDtoAssembler;

  public ParkingUserDtoAssembler(PermitDtoAssembler permitDtoAssembler) {
    this.permitDtoAssembler = permitDtoAssembler;
  }

  public ParkingUserResponse toResponse(ParkingUserDto dto) {
    ParkingUserResponse response = new ParkingUserResponse();
    response.id = dto.id.toString();
    response.name = dto.name;
    response.permits = permitDtoAssembler.toResponses(dto.permits);

    return response;
  }
}
