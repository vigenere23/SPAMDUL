package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.NoPassInfractionException;

public class EmptyCarParkingPassCodeValidator extends CarParkingPassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    if (passToValidateDto.passCode.equals("")) {
      throw new NoPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }

}
