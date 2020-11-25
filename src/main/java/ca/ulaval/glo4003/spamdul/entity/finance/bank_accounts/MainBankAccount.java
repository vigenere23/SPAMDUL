package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class MainBankAccount {

  private final TransactionFactory transactionFactory;
  private final TransactionRepository revenueTransactionRepository;

  public MainBankAccount(TransactionFactory transactionFactory, TransactionRepository revenueTransactionRepository) {
    this.transactionFactory = transactionFactory;
    this.revenueTransactionRepository = revenueTransactionRepository;
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
}
