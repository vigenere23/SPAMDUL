package ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionRepository;

public class MainBankAccount extends BaseBankAccount {


  public MainBankAccount(TransactionFactory transactionFactory,
                         TransactionRepository revenueTransactionRepository,
                         TransactionRepository expensesTransactionRepository) {
    super(transactionFactory, revenueTransactionRepository, expensesTransactionRepository);
  }

  @Override
  public String getName() {
    return "main";
  }
}
