package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.bank.InMemoryBankRepository;
import java.util.HashMap;
import java.util.Map;

public class AccountContext {

  private final BankRepository bankRepository;

  public AccountContext() {
    bankRepository = new InMemoryBankRepository();
    Account other = new Account(0.6);
    Account sustainableMobilityProjectAccount = new Account(0.4);
    MainBankAccount mainBankAccount = new MainBankAccount(sustainableMobilityProjectAccount, other);

    bankRepository.save(mainBankAccount);
    bankRepository.saveSustainableMobilityProjectAccount(sustainableMobilityProjectAccount);
  }

  public BankRepository bankRepository() {
    return bankRepository;
  }

}
