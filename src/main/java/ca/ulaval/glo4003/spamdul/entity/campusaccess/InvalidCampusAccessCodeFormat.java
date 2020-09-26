package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidAccessingCampusArgumentException;

public class InvalidCampusAccessCodeFormat extends InvalidAccessingCampusArgumentException {

  public InvalidCampusAccessCodeFormat(String message) {
    super(message);
  }
}
