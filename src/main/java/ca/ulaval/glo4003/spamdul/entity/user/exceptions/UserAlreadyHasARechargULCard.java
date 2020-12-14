package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserAlreadyHasARechargULCard extends InvalidUserException {

  public String getError() {
    return "USER_ALREADY_HAS_RECHARGUL_CARD";
  }

  public String getDescription() {
    return"User already has a rechargUL card" ;
  }
}
