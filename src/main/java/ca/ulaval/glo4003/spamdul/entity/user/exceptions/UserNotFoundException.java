package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidUserException;

public class UserNotFoundException extends InvalidUserException {

  public String getError() {
    return "INVALID_USER_ID";
  }

  public String getDescription() {
    return "User not found";
  }
}
