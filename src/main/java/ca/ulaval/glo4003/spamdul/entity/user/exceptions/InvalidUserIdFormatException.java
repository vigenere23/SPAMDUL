package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class InvalidUserIdFormatException extends InvalidUserException {

  public InvalidUserIdFormatException() {
    super("Invalid user id format");
  }
}
