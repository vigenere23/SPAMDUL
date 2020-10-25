package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.bank.InMemoryBankRepository;
import java.util.HashMap;
import java.util.Map;

public class AccountContext {

  private final BankRepository bankRepository;

  public AccountContext() {
    bankRepository = new InMemoryBankRepository();
    Map<Account, Double> accountRatioMap = new HashMap<>();
    Account mainAccount = new Account();
    accountRatioMap.put(mainAccount, 0.6);
    Account sustainableMobilityProjectAccount = new Account();
    accountRatioMap.put(sustainableMobilityProjectAccount, 0.4);
    Bank bank = new Bank(accountRatioMap);
    bankRepository.saveBank(bank);
    bankRepository.saveSustainableMobilityProjectAccount(sustainableMobilityProjectAccount);
  }

  public BankRepository bankRepository() {
    return bankRepository;
  }

}
