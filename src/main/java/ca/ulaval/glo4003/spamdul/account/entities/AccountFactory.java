package ca.ulaval.glo4003.spamdul.account.entities;

public class AccountFactory {

  private final AccountIdFactory accountIdFactory;

  public AccountFactory(AccountIdFactory accountIdFactory) {
    this.accountIdFactory = accountIdFactory;
  }

  public Account create() {
    return new Account(accountIdFactory.create());
  }
}
