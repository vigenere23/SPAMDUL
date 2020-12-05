package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserReaderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

public class PassExistsValidator extends PassValidator {
  private final UserReaderService userReader;

  public PassExistsValidator(UserReaderService userReaderService) {
    this.userReader = userReaderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      PassCode passCode = PassCode.valueOf(passToValidateDto.passCode);
      userReader.readUserBy(passCode);
    } catch (UserNotFoundException e) {
      throw new InvalidPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
