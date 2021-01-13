package ca.ulaval.glo4003.spamdul.account.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class AccountId extends Id {

  private AccountId(String value) {
    super(value);
  }

  public static AccountId valueOf(String value) {
    return new AccountId(value);
  }
}
