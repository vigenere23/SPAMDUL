package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;

public class CarParkingPassExistsValidator extends CarParkingPassValidator {
  private final UserFinderService userReader;

  public CarParkingPassExistsValidator(UserFinderService userFinderService) {
    this.userReader = userFinderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      CarParkingPassCode passCode = CarParkingPassCode.valueOf(passToValidateDto.passCode);
      userReader.findBy(passCode);
    } catch (UserNotFoundException e) {
      throw new InvalidPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
