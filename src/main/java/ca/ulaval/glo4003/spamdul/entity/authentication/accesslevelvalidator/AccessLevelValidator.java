package ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.UnauthorizedUserException;

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
