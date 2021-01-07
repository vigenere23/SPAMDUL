package ca.ulaval.glo4003.spamdul.account.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.Account;
import ca.ulaval.glo4003.spamdul.account.entities.AccountCreatedObservable;
import ca.ulaval.glo4003.spamdul.account.entities.AccountFactory;
import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.account.entities.AccountRepository;

public class AccountUseCase {

  private final AccountRepository accountRepository;
  private final AccountFactory accountFactory;
  private final AccountCreatedObservable accountCreatedObservable;

  public AccountUseCase(AccountRepository accountRepository,
                        AccountFactory accountFactory,
                        AccountCreatedObservable accountCreatedObservable) {
    this.accountRepository = accountRepository;
    this.accountFactory = accountFactory;
    this.accountCreatedObservable = accountCreatedObservable;
  }

  public AccountId createAccount() {
    Account account = accountFactory.create();
    accountRepository.save(account);

    accountCreatedObservable.notify(account.getId());

    return account.getId();
  }
}
