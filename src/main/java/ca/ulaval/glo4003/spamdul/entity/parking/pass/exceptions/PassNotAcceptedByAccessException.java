package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.exceptions.InvalidPassArgumentException;

public class PassNotAcceptedByAccessException extends InvalidPassArgumentException {

  public String getError() {
    return "NO_ACCESS";
  }

  public String getDescription() {
    return "This user does not have campus access for the dates covered by this pass";
  }
}
