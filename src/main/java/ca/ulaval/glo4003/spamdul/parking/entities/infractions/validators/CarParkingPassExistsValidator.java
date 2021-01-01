package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InvalidPassInfractionException;

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
