package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassArgumentException;

public class UserNotFoundException extends InvalidPassArgumentException {

  public UserNotFoundException() {
    super("User not found");
  }
}
