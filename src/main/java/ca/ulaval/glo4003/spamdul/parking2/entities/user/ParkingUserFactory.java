package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import java.time.LocalDate;

public class ParkingUserFactory {

  public ParkingUser create(AccountId accountId, String name, Sex sex, LocalDate birthDate) {
    return new ParkingUser(accountId, name, sex, birthDate);
  }
}
