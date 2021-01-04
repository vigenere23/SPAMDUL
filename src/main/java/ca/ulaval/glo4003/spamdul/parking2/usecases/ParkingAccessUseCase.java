package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionCreator;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;

public class ParkingAccessUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final InfractionCreator infractionCreator;

  public ParkingAccessUseCase(ParkingUserRepository parkingUserRepository,
                              InfractionCreator infractionCreator) {
    this.parkingUserRepository = parkingUserRepository;
    this.infractionCreator = infractionCreator;
  }

  public void accessParking(ParkingAccessDto dto) {
    if (dto.licensePlate == null && dto.permitNumber == null) {
      // TODO create specific exception here
      throw new RuntimeException("No permit specified.");
    }

    if (dto.licensePlate == null) {
      ParkingUser parkingUser = parkingUserRepository.findBy(dto.permitNumber);
      parkingUser.validateAccess(dto.permitNumber, dto.accessDateTime, dto.parkingZone);
    } else if (dto.permitNumber == null) {
      ParkingUser parkingUser = parkingUserRepository.findBy(dto.licensePlate);
      parkingUser.validateAccess(dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    } else {
      ParkingUser parkingUser = parkingUserRepository.findBy(dto.permitNumber);
      parkingUser.validateAccess(dto.permitNumber, dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    }
  }

  public void giveInfraction(ParkingAccessDto dto) {
    if (dto.licensePlate == null && dto.permitNumber == null) {
      // TODO create specific exception here
      throw new RuntimeException("No permit specified.");
    }

    ParkingUser parkingUser = null;

    try {
      if (dto.licensePlate == null) {
        parkingUser = parkingUserRepository.findBy(dto.permitNumber);
        parkingUser.validateAccess(dto.permitNumber, dto.accessDateTime, dto.parkingZone);
      } else if (dto.permitNumber == null) {
        parkingUser = parkingUserRepository.findBy(dto.licensePlate);
        parkingUser.validateAccess(dto.licensePlate, dto.accessDateTime, dto.parkingZone);
      } else {
        parkingUser = parkingUserRepository.findBy(dto.permitNumber);
        parkingUser.validateAccess(dto.permitNumber, dto.licensePlate, dto.accessDateTime, dto.parkingZone);
      }
    } catch (InvalidAccessException exception) {
      addInfractionToUser(parkingUser, exception);
      parkingUserRepository.save(parkingUser);
    }
  }

  private void addInfractionToUser(ParkingUser parkingUser, InvalidAccessException exception) {
    Infraction infraction = infractionCreator.createInfraction(exception);
    parkingUser.addInfraction(infraction);
  }
}
