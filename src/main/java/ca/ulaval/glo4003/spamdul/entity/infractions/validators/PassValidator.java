package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

import java.util.HashMap;
import java.util.Map;

public abstract class PassValidator {

  private static PassRepository passRepository;
  private final static Map <PassCode, Pass> passCache = new HashMap<>();
  protected PassValidator nextPassValidator;


  public static void setPassRepository(PassRepository passRepository) {
    PassValidator.passRepository = passRepository;
  }

  public void setNextValidator(PassValidator passValidator) {
    nextPassValidator = passValidator;
  }

  public void validate(PassToValidateDto passToValidateDto) {
    if (nextPassValidator != null) {
      nextPassValidator.validate(passToValidateDto);
    } else {
      terminateChainOfValidation(PassCode.valueOf(passToValidateDto.passCode));
    }
  }

  private void terminateChainOfValidation(PassCode passCode) {
    passCache.remove(passCode);
  }

  protected Pass getCorrespondingPass(PassCode passCode) {
    Pass correspondingPass = passCache.get(passCode);

    if (correspondingPass == null) {
      correspondingPass = passRepository.findByPassCode(passCode);
      passCache.put(passCode, correspondingPass);
    }

    return correspondingPass;
  }
}
