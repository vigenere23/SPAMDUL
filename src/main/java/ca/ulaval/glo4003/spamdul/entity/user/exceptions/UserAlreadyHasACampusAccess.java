package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasACampusAccess extends InvalidUserException {

  public String getError() {
    return "USER_ALREADY_HAS_CAMPUS_ACCESS";
  }

  public String getDescription() {
    return "User already has a campus access asociated";
  }
}
