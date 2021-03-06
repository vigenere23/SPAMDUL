package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasARechargULCard extends InvalidUserException {

  public String getError() {
    return "USER_ALREADY_HAS_RECHARGUL_CARD";
  }

  public String getDescription() {
    return"User already has a rechargUL card" ;
  }
}
