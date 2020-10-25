package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.InvalidPassCodeFormat;

public class PassCodeFormatValidator extends PassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      PassCode.valueOf(passToValidateDto.passCode);
    } catch (InvalidPassCodeFormat e) {
      throw new InfractionException("VIG_02");
    }

    nextValidation(passToValidateDto);
  }
}