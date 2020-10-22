package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;

public class ValidationChain {

  private PassValidator baseValidator;

  public ValidationChain(PassValidator baseValidator) {
    this.baseValidator = baseValidator;
  }

  public void validate(PassToValidateDto passToValidateDto) {
    baseValidator.validate(passToValidateDto);
  }
}
