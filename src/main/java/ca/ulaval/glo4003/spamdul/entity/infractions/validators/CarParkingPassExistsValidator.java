package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

public class CarParkingPassExistsValidator extends CarParkingPassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      getCorrespondingPass(CarParkingPassCode.valueOf(passToValidateDto.passCode));
    } catch (UserNotFoundException e) {
      throw new InfractionException("VIG_02");
    }

    nextValidation(passToValidateDto);
  }
}
