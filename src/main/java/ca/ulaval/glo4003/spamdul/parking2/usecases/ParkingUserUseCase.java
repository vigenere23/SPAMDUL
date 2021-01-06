package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.ParkingUserAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;

public class ParkingUserUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingUserFactory parkingUserFactory;
  private final ParkingUserAssembler parkingUserAssembler;

  public ParkingUserUseCase(ParkingUserRepository parkingUserRepository,
                            ParkingUserFactory parkingUserFactory,
                            ParkingUserAssembler parkingUserAssembler) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.parkingUserAssembler = parkingUserAssembler;
  }

  public ParkingUserDto getUser(ParkingUserId parkingUserId) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);

    return parkingUserAssembler.toDto(parkingUser);
  }

  public ParkingUserDto createUser(UserCreationDto dto) {
    ParkingUser parkingUser = parkingUserFactory.create(dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(parkingUser);

    return parkingUserAssembler.toDto(parkingUser);
  }
}
