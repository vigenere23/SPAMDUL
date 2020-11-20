package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessingCampusArgumentException;

public class InvalidCampusAccessCodeFormatException extends InvalidAccessingCampusArgumentException {

  public InvalidCampusAccessCodeFormatException(String message) {
    super(message);
  }
}
