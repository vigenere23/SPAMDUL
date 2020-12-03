package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassArgumentException;

public class PassAlreadyAssociatedException extends InvalidPassArgumentException {

  public PassAlreadyAssociatedException() {
    super("this user already has a pass for this date");
  }
}
