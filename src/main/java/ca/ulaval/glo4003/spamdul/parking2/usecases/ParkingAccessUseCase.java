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
    ParkingUser parkingUser = findParkingUser(dto);

    validateAccess(parkingUser, dto);
  }

  public void giveInfraction(ParkingAccessDto dto) {
    ParkingUser parkingUser = findParkingUser(dto);

    try {
      validateAccess(parkingUser, dto);
    } catch (InvalidAccessException exception) {
      addInfractionToUser(parkingUser, exception);
      parkingUserRepository.save(parkingUser);
    }
  }

  private ParkingUser findParkingUser(ParkingAccessDto dto) {
    validateRequiredInfos(dto);

    if (dto.permitNumber != null) {
      return parkingUserRepository.findBy(dto.permitNumber);
    } else {
      return parkingUserRepository.findBy(dto.licensePlate);
    }
  }

  private void validateAccess(ParkingUser parkingUser, ParkingAccessDto dto) {
    validateRequiredInfos(dto);
    
    if (dto.licensePlate == null) {
      parkingUser.validateAccess(dto.permitNumber, dto.accessDateTime, dto.parkingZone);
    } else if (dto.permitNumber == null) {
      parkingUser.validateAccess(dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    } else {
      parkingUser.validateAccess(dto.permitNumber, dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    }
  }

  private void validateRequiredInfos(ParkingAccessDto dto) {
    if (dto.permitNumber == null && dto.licensePlate == null) {
      throw new IllegalArgumentException("Either a permit number or a license plate is required.");
    }
  }

  private void addInfractionToUser(ParkingUser parkingUser, InvalidAccessException exception) {
    Infraction infraction = infractionCreator.createInfraction(exception);
    parkingUser.addInfraction(infraction);
  }
}
