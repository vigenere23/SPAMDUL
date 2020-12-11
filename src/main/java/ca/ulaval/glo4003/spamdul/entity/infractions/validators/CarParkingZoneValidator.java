package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;

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
