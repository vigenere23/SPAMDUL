package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;

public class MainBankAccount extends GeneralBankAccount {

  public MainBankAccount(TransactionFactory transactionFactory, TransactionRepository transactionRepository) {
    super(transactionFactory, transactionRepository);
  }
}
