package ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usecases.infraction.UnauthorizedUserException;

public class InfractionsAccessLevelValidator implements AccessLevelValidator {

  private final AccessLevel REQUIRED_ACCESS_LEVEL = AccessLevel.SSP_AGENT;
  private AuthenticationRepository authenticationRepository;

  public InfractionsAccessLevelValidator(AuthenticationRepository authenticationRepository) {
    this.authenticationRepository = authenticationRepository;
  }


  public void validate(TemporaryToken temporaryToken) {
    try {
      boolean hasTheRightAccessLevel = authenticationRepository.findBy(temporaryToken).hasTheRightAccessLevel(
          REQUIRED_ACCESS_LEVEL);

      if (!hasTheRightAccessLevel) {
        throw new UnauthorizedUserException();
      }
    } catch (NoRegisteredUserLoggedInException e) {
      throw new UnauthorizedUserException();
    }
  }
}
