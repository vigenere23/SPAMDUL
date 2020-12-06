package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasThisInfraction extends InvalidUserException {

  public UserAlreadyHasThisInfraction() {
    super("User already has this infraction associated");
  }
}
