package ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;

public interface AccessLevelValidator {

  void validate(TemporaryToken temporaryToken);
}
