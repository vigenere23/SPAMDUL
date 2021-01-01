package ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidUserException;

public class UserMustOwnACarToPurchaseACarParkingPassException extends InvalidUserException {

  @Override public String getError() {
    return "USER_MUST_OWN_A_CAR";
  }

  @Override public String getDescription() {
    return "user must own a car to purchase a car parking pass";
  }
}
