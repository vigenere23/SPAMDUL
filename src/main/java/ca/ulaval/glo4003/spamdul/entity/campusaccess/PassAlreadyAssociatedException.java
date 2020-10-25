package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassArgumentException;

public class PassAlreadyAssociatedException extends InvalidPassArgumentException {

  public PassAlreadyAssociatedException(String message) {
    super(message);
  }
}
