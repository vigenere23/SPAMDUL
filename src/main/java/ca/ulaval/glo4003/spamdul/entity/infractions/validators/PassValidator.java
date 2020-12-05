package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

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

}
