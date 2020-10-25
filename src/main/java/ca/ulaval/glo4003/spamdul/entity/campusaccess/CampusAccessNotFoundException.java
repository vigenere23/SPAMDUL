package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassArgumentException;

public class CampusAccessNotFoundException extends InvalidPassArgumentException {

  public CampusAccessNotFoundException(String message) {
    super(message);
  }
}
