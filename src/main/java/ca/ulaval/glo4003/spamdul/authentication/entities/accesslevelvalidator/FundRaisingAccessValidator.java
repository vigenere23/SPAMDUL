package ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.authentication.entities.AccessLevel;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;

public class FundRaisingAccessValidator extends AccessLevelValidator {

  private final AccessLevel REQUIRED_ACCESS_LEVEL = AccessLevel.ADMIN;

  public FundRaisingAccessValidator(AuthenticationRepository authenticationRepository) {
    super(authenticationRepository);
  }

  public void validate(TemporaryToken temporaryToken) {
    validate(temporaryToken, REQUIRED_ACCESS_LEVEL);
  }
}
