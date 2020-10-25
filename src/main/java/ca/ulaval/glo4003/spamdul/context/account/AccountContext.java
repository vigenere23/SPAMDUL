package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.bank.InMemoryBankRepository;

public class AccountContext {

  private final BankRepository bankRepository;

  public AccountContext() {
    bankRepository = new InMemoryBankRepository();
    TransactionFactory transactionFactory = new TransactionFactory();
    Account other = new Account();
    Account sustainableMobilityProjectAccount = new Account();
    double sustainableMobilityProjectRatio = 0.4;
    MainBankAccount mainBankAccount = new MainBankAccount(transactionFactory,
                                                          sustainableMobilityProjectAccount,
                                                          other,
                                                          sustainableMobilityProjectRatio);

    bankRepository.save(mainBankAccount);
    bankRepository.saveSustainableMobilityProjectAccount(sustainableMobilityProjectAccount);
  }

  public BankRepository bankRepository() {
    return bankRepository;
  }

}
