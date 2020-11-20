package ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;

public class CarbonCreditsAccessLevelValidator extends AccessLevelValidator {

  private final AccessLevel REQUIRED_ACCESS_LEVEL = AccessLevel.ADMIN;

  public CarbonCreditsAccessLevelValidator(AuthenticationRepository authenticationRepository) {
    super(authenticationRepository);
  }

  public void validate(TemporaryToken temporaryToken) {
    validate(temporaryToken, REQUIRED_ACCESS_LEVEL);
  }
}
