package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.PassRepositoryNotSetException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;

public abstract class PassValidator {

  private static UserRepository userRepository;
  protected final static Map<PassCode, Pass> passCache = new HashMap<>();
  protected PassValidator nextPassValidator;


  public static void setPassRepository(UserRepository userRepository) {
    PassValidator.userRepository = userRepository;
  }

  public void setNextValidator(PassValidator passValidator) {
    nextPassValidator = passValidator;
  }

  public abstract void validate(PassToValidateDto passToValidateDto);

  protected void nextValidation(PassToValidateDto passToValidateDto) {
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
      if (userRepository == null) {
        throw new PassRepositoryNotSetException("This validator requires pass repository " +
                                                    "to be set before usage");
      }

      correspondingPass = userRepository.findBy(passCode).getPass();
      passCache.put(passCode, correspondingPass);
    }

    return correspondingPass;
  }
}
