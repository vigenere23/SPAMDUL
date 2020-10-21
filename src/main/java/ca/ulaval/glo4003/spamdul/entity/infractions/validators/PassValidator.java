package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public abstract class PassValidator {

  private final PassRepository passRepository;
  private static Pass correspondingPass;
  protected PassValidator nextPassValidator;

  public PassValidator(PassRepository passRepository) {
    this.passRepository = passRepository;
  }

  public void setNextValidator(PassValidator passValidator) {
    nextPassValidator = passValidator;
  }

  public void validate(PassToValidateDto passToValidateDto) {
    if (nextPassValidator != null) {
      nextPassValidator.validate(passToValidateDto);
    } else {
      terminateChainOfValidation();
    }
  }

  private void terminateChainOfValidation() {
    correspondingPass = null;
  }

  protected Pass getCorrespondingPass(PassCode passCode) {
    if (correspondingPass == null) {
      correspondingPass = passRepository.findByPassCode(passCode);
    }
    return correspondingPass;
  }
}
