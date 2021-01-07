package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class ParkingUserNotFound extends RuntimeException {

  public ParkingUserNotFound(AccountId accountId) {
    super(String.format("Parking user with account id %s was not found.", accountId));
  }
}
