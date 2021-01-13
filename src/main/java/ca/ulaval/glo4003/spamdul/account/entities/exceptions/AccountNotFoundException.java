package ca.ulaval.glo4003.spamdul.account.entities.exceptions;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(AccountId accountId) {
    super(String.format("Account with id %s was not found", accountId));
  }
}
