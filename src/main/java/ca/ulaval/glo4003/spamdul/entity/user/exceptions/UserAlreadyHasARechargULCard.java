package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasARechargULCard extends InvalidUserException {

  public UserAlreadyHasARechargULCard() {
    super("User already has a rechargUL card");
  }
}
