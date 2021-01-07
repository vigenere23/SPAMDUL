package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.account.entities.AccountService;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.ParkingUserAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;

public class ParkingUserUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingUserFactory parkingUserFactory;
  private final ParkingUserAssembler parkingUserAssembler;
  private final AccountService accountService;

  public ParkingUserUseCase(ParkingUserRepository parkingUserRepository,
                            ParkingUserFactory parkingUserFactory,
                            ParkingUserAssembler parkingUserAssembler,
                            AccountService accountService) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.parkingUserAssembler = parkingUserAssembler;
    this.accountService = accountService;
  }

  public ParkingUserDto getUser(AccountId accountId) {
    ParkingUser parkingUser = parkingUserRepository.findBy(accountId);

    return parkingUserAssembler.toDto(parkingUser);
  }

  public ParkingUserDto createUser(AccountId accountId, UserCreationDto dto) {
    accountService.ensureExists(accountId);
    // TODO verify that parking user does not already exist
    ParkingUser parkingUser = parkingUserFactory.create(accountId, dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(parkingUser);

    return parkingUserAssembler.toDto(parkingUser);
  }
}
