package ca.ulaval.glo4003.spamdul.account.entities;

public class Account {

  private final AccountId accountId;

  public Account(AccountId accountId) {
    this.accountId = accountId;
  }

  public AccountId getId() {
    return accountId;
  }
}
