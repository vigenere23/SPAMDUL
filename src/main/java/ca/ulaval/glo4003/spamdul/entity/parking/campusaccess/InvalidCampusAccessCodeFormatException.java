package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidAccessingCampusArgumentException;

public class InvalidCampusAccessCodeFormatException extends InvalidAccessingCampusArgumentException {

  public InvalidCampusAccessCodeFormatException(String message) {
    super(message);
  }
}
