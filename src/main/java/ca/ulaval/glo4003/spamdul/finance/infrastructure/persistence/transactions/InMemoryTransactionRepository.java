package ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.transactions;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements TransactionRepository {

  private final Map<TransactionType, List<Transaction>> transactionsByType = new HashMap<>();

  @Override
  public List<Transaction> findAll() {
    List<Transaction> allTransactions = new ArrayList<>();
    transactionsByType.values().forEach(allTransactions::addAll);
    return allTransactions;
  }

  @Override
  public List<Transaction> findAll(TransactionFilter transactionFilter) {
    return transactionFilter.setData(findAll()).getResults();
  }

  @Override
  public List<Transaction> findAllBy(TransactionType transactionType) {
    return new ArrayList<>(transactionsByType.getOrDefault(transactionType, new ArrayList<>()));
  }

  @Override
  public List<Transaction> findAllBy(TransactionType transactionType, TransactionFilter transactionFilter) {
    return transactionFilter.setData(findAllBy(transactionType)).getResults();
  }

  @Override public void save(Transaction transaction) {
    List<Transaction> transactions = findAllBy(transaction.getTransactionType());
    transactions.add(transaction);
    transactionsByType.put(transaction.getTransactionType(), transactions);
  }
}
