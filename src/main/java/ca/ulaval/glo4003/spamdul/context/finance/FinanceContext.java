package ca.ulaval.glo4003.spamdul.context.finance;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CampusAccessBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InfractionBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativesBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.PassBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryCampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryTransactionRepository;

public class FinanceContext {

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CarbonCreditsBankAccount carbonCreditsBankAccount;

  private final CampusAccessBankAccount campusAccessBankAccount;
  private final InfractionBankAccount infractionBankAccount;
  private final InitiativesBankAccount initiativesBankAccount;
  private final PassBankAccount passBankAccount;

  public FinanceContext() {
    TransactionFactory transactionFactory = new TransactionFactory();

    mainBankAccount = new MainBankAccount(transactionFactory, new InMemoryTransactionRepository());
    sustainabilityBankAccount = new SustainabilityBankAccount(transactionFactory, new InMemoryTransactionRepository(),
                                                              new InMemoryTransactionRepository());
    initiativesBankAccount = new InitiativesBankAccount(sustainabilityBankAccount);
    carbonCreditsBankAccount = new CarbonCreditsBankAccount(transactionFactory,
                                                            initiativesBankAccount,
                                                            new InMemoryTransactionRepository());
    campusAccessBankAccount = new CampusAccessBankAccount(mainBankAccount,
                                                          sustainabilityBankAccount,
                                                          new InMemoryCampusAccessTransactionRepository());
    infractionBankAccount = new InfractionBankAccount(mainBankAccount, sustainabilityBankAccount);
    passBankAccount = new PassBankAccount(mainBankAccount, sustainabilityBankAccount);
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

  public CarbonCreditsBankAccount getCarbonCreditsBankAccount() {
    return carbonCreditsBankAccount;
  }

  public InitiativesBankAccount getInitiativesBankAccount() {
    return initiativesBankAccount;
  }

  public PassBankAccount getPassBankAccount() {
    return passBankAccount;
  }
}
