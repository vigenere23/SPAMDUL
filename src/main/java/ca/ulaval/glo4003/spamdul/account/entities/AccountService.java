package ca.ulaval.glo4003.spamdul.account.entities;

public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void ensureExists(AccountId accountId) {
    accountRepository.find(accountId);
  }
}
