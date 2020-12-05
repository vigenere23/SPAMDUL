package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

public class PassExistsValidator extends PassValidator {
  private final UserFinderService userReader;

  public PassExistsValidator(UserFinderService userFinderService) {
    this.userReader = userFinderService;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    try {
      PassCode passCode = PassCode.valueOf(passToValidateDto.passCode);
      userReader.findBy(passCode);
    } catch (UserNotFoundException e) {
      throw new InvalidPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }
}
