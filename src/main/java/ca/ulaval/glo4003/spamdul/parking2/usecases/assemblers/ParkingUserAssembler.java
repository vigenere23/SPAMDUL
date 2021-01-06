package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;

public class ParkingUserAssembler {

  private final PermitsAssembler permitsAssembler;

  public ParkingUserAssembler(PermitsAssembler permitsAssembler) {
    this.permitsAssembler = permitsAssembler;
  }

  public ParkingUserDto toDto(ParkingUser parkingUser) {
    ParkingUserDto dto = new ParkingUserDto();

    dto.id = parkingUser.getId();
    dto.name = parkingUser.getName();
    dto.permits = permitsAssembler.toDtos(parkingUser.getPermits());

    return dto;
  }
}
