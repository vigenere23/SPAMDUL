package ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.authentication.entities.AccessLevel;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.UnauthorizedUserException;

public abstract class AccessLevelValidator {

  private final AuthenticationRepository authenticationRepository;

  public AccessLevelValidator(AuthenticationRepository authenticationRepository) {
    this.authenticationRepository = authenticationRepository;
  }

  public abstract void validate(TemporaryToken temporaryToken);

  protected void validate(TemporaryToken temporaryToken, AccessLevel requiredAccessLevel) {
    boolean hasTheRightAccessLevel = authenticationRepository.findBy(temporaryToken).hasTheRightAccessLevel(
        requiredAccessLevel);

    if (!hasTheRightAccessLevel) {
      throw new UnauthorizedUserException();
    }
  }
}
