package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.bank.InMemoryBankRepository;

public class AccountContext {

  private final BankRepository bankRepository;

  public AccountContext() {
    bankRepository = new InMemoryBankRepository();
    TransactionFactory transactionFactory = new TransactionFactory();
    SustainabilityBankAccount other = new SustainabilityBankAccount();
    SustainabilityBankAccount sustainabilityBankAccount = new SustainabilityBankAccount();
    CarbonCreditsBankAccount carbonCreditsBankAccount = new CarbonCreditsBankAccount();
    double sustainableMobilityProjectRatio = 0.4;
    MainBankAccount mainBankAccount = new MainBankAccount(transactionFactory,
                                                          sustainabilityBankAccount,
                                                          other,
                                                          sustainableMobilityProjectRatio);

    bankRepository.save(mainBankAccount);
    bankRepository.save(sustainabilityBankAccount);
    bankRepository.save(carbonCreditsBankAccount);
  }

  public BankRepository bankRepository() {
    return bankRepository;
  }

}
