package ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts;

import ca.ulaval.glo4003.spamdul.finance.entities.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionAmountQuerier;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

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
      throw new InsufficientFundsException(getName(), amount, balance);
    }
  }

  public TransactionAmountQuerier getRevenue() {
    return new TransactionAmountQuerier(revenueTransactionRepository);
  }

  public TransactionAmountQuerier getExpenses() {
    return new TransactionAmountQuerier(expensesTransactionRepository);
  }

  public Amount getBalance() {
    return getRevenue().total().add(getExpenses().total());
  }

  public abstract String getName();
}
