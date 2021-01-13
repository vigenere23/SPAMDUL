package ca.ulaval.glo4003.spamdul.account.context;

import ca.ulaval.glo4003.spamdul.account.api.AccountResource;
import ca.ulaval.glo4003.spamdul.account.api.AccountUriBuilder;
import ca.ulaval.glo4003.spamdul.account.entities.AccountCreatedObservable;
import ca.ulaval.glo4003.spamdul.account.entities.AccountFactory;
import ca.ulaval.glo4003.spamdul.account.entities.AccountIdFactory;
import ca.ulaval.glo4003.spamdul.account.entities.AccountRepository;
import ca.ulaval.glo4003.spamdul.account.entities.AccountService;
import ca.ulaval.glo4003.spamdul.account.infrastructure.persistence.AccountRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.account.usecases.AccountUseCase;
import ca.ulaval.glo4003.spamdul.shared.api.ApiUrl;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class AccountContext implements ResourceContext {

  private final AccountResource accountResource;
  private final AccountService accountService;
  private final AccountCreatedObservable accountCreatedObservable;

  public AccountContext(ApiUrl apiUrl) {
    AccountRepository accountRepository = new AccountRepositoryInMemory();
    accountService = new AccountService(accountRepository);
    AccountFactory accountFactory = new AccountFactory(new AccountIdFactory(new IncrementalIdGenerator()));
    accountCreatedObservable = new AccountCreatedObservable();
    AccountUseCase accountUseCase = new AccountUseCase(accountRepository, accountFactory, accountCreatedObservable);

    accountResource = new AccountResource(accountUseCase, new AccountUriBuilder(apiUrl.toString()));
  }

  public AccountService getAccountService() {
    return accountService;
  }

  public AccountCreatedObservable getAccountCreatedObservable() {
    return accountCreatedObservable;
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(accountResource);
  }
}
