package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.exceptions.InvalidUserArgumentException;

public class InvalidDayToAccessCampusException extends InvalidUserArgumentException {

  public InvalidDayToAccessCampusException(String message) {
    super(message);
  }
}
