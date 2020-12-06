package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasACampusAccess extends InvalidUserException {

  public UserAlreadyHasACampusAccess() {
    super("User already has a campus access asociated");
  }
}
