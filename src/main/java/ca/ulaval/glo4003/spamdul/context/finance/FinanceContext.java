package ca.ulaval.glo4003.spamdul.context.finance;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CampusAccessBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InfractionBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryCampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryTransactionRepository;

public class FinanceContext {

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CampusAccessBankAccount campusAccessBankAccount;
  private final InfractionBankAccount infractionBankAccount;

  public FinanceContext() {
    TransactionFactory transactionFactory = new TransactionFactory();

    mainBankAccount = new MainBankAccount(transactionFactory, new InMemoryTransactionRepository());
    sustainabilityBankAccount = new SustainabilityBankAccount(transactionFactory, new InMemoryTransactionRepository());

    campusAccessBankAccount = new CampusAccessBankAccount(mainBankAccount,
                                                          sustainabilityBankAccount,
                                                          new InMemoryCampusAccessTransactionRepository());
    infractionBankAccount = new InfractionBankAccount(mainBankAccount, sustainabilityBankAccount);
  }

  public MainBankAccount getMainBankAccount() {
    return mainBankAccount;
  }

  public SustainabilityBankAccount getSustainabilityBankAccount() {
    return sustainabilityBankAccount;
  }

  public CampusAccessBankAccount getCampusAccessBankAccount() {
    return campusAccessBankAccount;
  }

  public InfractionBankAccount getInfractionBankAccount() {
    return infractionBankAccount;
  }
}
