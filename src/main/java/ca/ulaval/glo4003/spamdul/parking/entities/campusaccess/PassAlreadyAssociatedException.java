package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.exceptions.InvalidPassArgumentException;

public class PassAlreadyAssociatedException extends InvalidPassArgumentException {

  public String getError() {
    return "PASS_ALREADY_ASSOCIATED";
  }

  public String getDescription() {
    return "This user already has a pass for this date";
  }
}
