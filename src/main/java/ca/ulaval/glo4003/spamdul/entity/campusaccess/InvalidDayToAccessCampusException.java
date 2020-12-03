package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessArgumentException;

public class InvalidDayToAccessCampusException extends InvalidCampusAccessArgumentException {

  public InvalidDayToAccessCampusException() {
    super("Invalid day to access campus");
  }
}
