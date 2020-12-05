package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.NoPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;

public class EmptyPassCodeValidator extends PassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    if (passToValidateDto.passCode.equals("")) {
      throw new NoPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }

}
