package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;

public class EmptyCarParkingPassCodeValidator extends CarParkingPassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    if (passToValidateDto.passCode.equals("")) {
      throw new InfractionException("VIG_03");
    }

    nextValidation(passToValidateDto);
  }
}
