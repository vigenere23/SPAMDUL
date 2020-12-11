package ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;

public class UserMustOwnACarToPurchaseACarParkingPassException extends InvalidUserException {

  public UserMustOwnACarToPurchaseACarParkingPassException() {
    super("user must own a car to purchase a car parking pass");
  }
}
