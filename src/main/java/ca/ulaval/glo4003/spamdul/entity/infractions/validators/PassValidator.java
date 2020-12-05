package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;

public abstract class PassValidator {

  private PassValidator nextPassValidator;

  public void setNextValidator(PassValidator passValidator) {
    nextPassValidator = passValidator;
  }

  public abstract void validate(PassToValidateDto passToValidateDto);

  protected void nextValidation(PassToValidateDto passToValidateDto) {
    if (nextPassValidator != null) {
      nextPassValidator.validate(passToValidateDto);
    }
  }

  protected void giveInfraction(InfractionException infractionException) {
    throw infractionException;
  }

}
