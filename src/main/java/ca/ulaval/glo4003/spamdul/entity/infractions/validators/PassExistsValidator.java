package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;

public class PassExistsValidator extends PassValidator {

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));
    } catch (UserNotFoundException e) {
      throw new InfractionException("VIG_02");
    }

    nextValidation(passToValidateDto);
  }
}
