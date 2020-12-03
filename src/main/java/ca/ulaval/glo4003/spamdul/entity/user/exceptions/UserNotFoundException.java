package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserNotFoundException extends InvalidUserException {

  public UserNotFoundException() {
    super("User not found");
  }
}
