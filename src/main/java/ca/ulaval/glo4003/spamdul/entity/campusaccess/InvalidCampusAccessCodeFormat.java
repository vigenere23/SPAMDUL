package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessingCampusArgumentException;

public class InvalidCampusAccessCodeFormat extends InvalidAccessingCampusArgumentException {

  public InvalidCampusAccessCodeFormat(String message) {
    super(message);
  }
}
