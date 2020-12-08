package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidAccessingCampusArgumentException;

public class PassNotFoundException extends InvalidAccessingCampusArgumentException {

  public PassNotFoundException(String message) {
    super(message);
  }
}
