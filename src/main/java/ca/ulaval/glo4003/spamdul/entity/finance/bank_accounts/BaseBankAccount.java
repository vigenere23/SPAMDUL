package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionAmountQueryer;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public abstract class BaseBankAccount {

  protected final TransactionFactory transactionFactory;
  protected final TransactionRepository revenueTransactionRepository;
  protected final TransactionRepository expensesTransactionRepository;

  protected BaseBankAccount(TransactionFactory transactionFactory,
                            TransactionRepository revenueTransactionRepository,
                            TransactionRepository expensesTransactionRepository) {
    this.transactionFactory = transactionFactory;
    this.revenueTransactionRepository = revenueTransactionRepository;
    this.expensesTransactionRepository = expensesTransactionRepository;
  }

  public Transaction addRevenue(Amount amount, TransactionType transactionType) {
    Transaction transaction = transactionFactory.create(transactionType, amount);
    revenueTransactionRepository.save(transaction);
    return transaction;
  }

  public Transaction addExpense(Amount amount, TransactionType transactionType) {
    Transaction transaction = transactionFactory.create(transactionType, amount.multiply(-1));
    expensesTransactionRepository.save(transaction);
    return transaction;
  }

  protected void verifyEnoughFundsForAmount(Amount amount) {
    Amount balance = getBalance();
    if (balance.subtract(amount).isStrictlyNegative()) {
      throw new InsufficientFundsException("sustainability account", amount, balance);
    }
  }

  public TransactionAmountQueryer getRevenue() {
    return new TransactionAmountQueryer(revenueTransactionRepository);
  }

  public TransactionAmountQueryer getExpenses() {
    return new TransactionAmountQueryer(expensesTransactionRepository);
  }

  public Amount getBalance() {
    return getRevenue().total().add(getExpenses().total());
  }
}
