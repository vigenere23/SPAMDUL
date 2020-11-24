package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public abstract class GeneralBankAccount {

  protected final TransactionFactory transactionFactory;
  protected final TransactionRepository transactionRepository;

  public GeneralBankAccount(TransactionFactory transactionFactory, TransactionRepository transactionRepository) {
    this.transactionFactory = transactionFactory;
    this.transactionRepository = transactionRepository;
  }

  public Transaction addExpense(Amount amount, TransactionType transactionType) {
    Transaction transaction = transactionFactory.create(transactionType, amount.multiply(-1));
    transactionRepository.save(transaction);
    return transaction;
  }

  public Transaction addRevenue(Amount amount, TransactionType transactionType) {
    Transaction transaction = transactionFactory.create(transactionType, amount);
    transactionRepository.save(transaction);
    return transaction;
  }

  public Amount getBalance() {
    TransactionList transactionList = new TransactionList(transactionRepository.findAll());
    return transactionList.getBalance();
  }

  public List<Transaction> getTransactionsOfType(TransactionType transactionType) {
    return transactionRepository.findAllBy(transactionType);
  }
}
