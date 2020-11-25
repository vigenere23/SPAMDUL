package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class SustainabilityBankAccount {

  private final TransactionFactory transactionFactory;
  private final TransactionRepository revenueTransactionRepository;
  private final TransactionRepository expenseTransactionRepository;

  public SustainabilityBankAccount(TransactionFactory transactionFactory,
                                   TransactionRepository revenueTransactionRepository,
                                   TransactionRepository expenseTransactionRepository) {
    this.transactionFactory = transactionFactory;
    this.revenueTransactionRepository = revenueTransactionRepository;
    this.expenseTransactionRepository = expenseTransactionRepository;
  }

  public Transaction addRevenue(Amount amount, TransactionType transactionType) {
    Transaction transaction = transactionFactory.create(transactionType, amount);
    revenueTransactionRepository.save(transaction);
    return transaction;
  }

  public Amount getRevenue() {
    List<Transaction> transactions = revenueTransactionRepository.findAll();
    return new TransactionList(transactions).getBalance();
  }

  public Amount getRevenue(TransactionType transactionType) {
    List<Transaction> transactions = revenueTransactionRepository.findAllBy(transactionType);
    return new TransactionList(transactions).getBalance();
  }

  public Amount getRevenue(TransactionType transactionType, TransactionFilter transactionFilter) {
    List<Transaction> transactions = revenueTransactionRepository.findAllBy(transactionType, transactionFilter);
    return new TransactionList(transactions).getBalance();
  }

  public Transaction addExpense(Amount amount, TransactionType transactionType) {
    verifyEnoughFundsForAmount(amount);

    Transaction transaction = transactionFactory.create(transactionType, amount.multiply(-1));
    expenseTransactionRepository.save(transaction);
    return transaction;
  }

  public Amount getExpenses() {
    List<Transaction> transactions = expenseTransactionRepository.findAll();
    return new TransactionList(transactions).getBalance();
  }

  public Amount getBalance() {
    return getRevenue().add(getExpenses());
  }

  private void verifyEnoughFundsForAmount(Amount amount) {
    Amount balance = getBalance();
    if (balance.subtract(amount).isStrictlyNegative()) {
      throw new InsufficientFundsException("sustainability account", amount, balance);
    }
  }
}
