package ca.ulaval.glo4003.spamdul.account.entities;

public interface AccountCreatedObserver {

  void handleAccountCreated(AccountId accountId);
}
