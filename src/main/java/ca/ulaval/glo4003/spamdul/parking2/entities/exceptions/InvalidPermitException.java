package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;

public class InvalidPermitException extends InvalidAccessException {

  public InvalidPermitException(PermitNumber permitNumber) {
    super(String.format("Permit with number %s is invalid", permitNumber));
  }
}
