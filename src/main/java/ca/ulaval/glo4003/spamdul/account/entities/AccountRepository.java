package ca.ulaval.glo4003.spamdul.account.entities;

public interface AccountRepository {

  Account find(AccountId accountId);

  void save(Account account);
}
