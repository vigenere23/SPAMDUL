package ca.ulaval.glo4003.spamdul.parking2.entities.exceptions;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class ParkingUserNotFoundException extends RuntimeException {

  public ParkingUserNotFoundException(AccountId accountId) {
    super(String.format("Parking user with account id %s was not found.", accountId));
  }
}
