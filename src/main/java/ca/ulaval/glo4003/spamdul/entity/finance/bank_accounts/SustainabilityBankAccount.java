package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class SustainabilityBankAccount extends BaseBankAccount {

  public SustainabilityBankAccount(TransactionFactory transactionFactory,
                                   TransactionRepository revenueTransactionRepository,
                                   TransactionRepository expensesTransactionRepository) {
    super(transactionFactory, revenueTransactionRepository, expensesTransactionRepository);
  }

  @Override
  public Transaction addExpense(Amount amount, TransactionType transactionType) {
    verifyEnoughFundsForAmount(amount);

    return super.addExpense(amount, transactionType);
  }

  @Override
  public String getName() {
    return "sustainability";
  }
}
