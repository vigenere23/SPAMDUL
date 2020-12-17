package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;

public class CarParkingZoneValidator extends CarParkingPassValidator {
  private final UserFinderService userReader;

  public CarParkingZoneValidator(UserFinderService userFinderService) {
    this.userReader = userFinderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    CarParkingPassCode passCode = CarParkingPassCode.valueOf(passToValidateDto.passCode);
    User user = userReader.findBy(passCode);

    if (!user.canParkInZone(passToValidateDto.parkingZone)) {
      throw new WrongZoneInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
