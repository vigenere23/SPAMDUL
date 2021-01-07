package ca.ulaval.glo4003.spamdul.account.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.account.entities.Account;
import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.account.entities.AccountRepository;

public class AccountRepositoryInMemory implements AccountRepository {

  @Override
  public Account find(AccountId accountId) {
    return null;
  }

  @Override
  public void save(Account account) {

  }
}
