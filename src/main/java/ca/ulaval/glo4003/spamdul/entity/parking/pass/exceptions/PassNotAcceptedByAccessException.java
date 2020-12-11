package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions.InvalidPassArgumentException;

public class PassNotAcceptedByAccessException extends InvalidPassArgumentException {

  public PassNotAcceptedByAccessException() {
    super("This user does not have campus access for the dates covered by this pass");
  }
}
