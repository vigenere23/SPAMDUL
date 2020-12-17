package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasThisInfraction extends InvalidUserException {

  public String getError() {
    return "USER_ALREADY_HAS_THIS_INFRACTION";
  }

  public String getDescription() {
    return "User already has this infraction associated";
  }
}
