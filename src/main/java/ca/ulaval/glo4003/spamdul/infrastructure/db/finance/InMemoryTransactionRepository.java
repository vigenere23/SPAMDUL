package ca.ulaval.glo4003.spamdul.infrastructure.db.finance;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryTransactionRepository implements TransactionRepository {

  private final Map<TransactionType, Transaction> transactions = new HashMap<>();

  @Override public List<Transaction> findAll() {
    return Lists.newArrayList(transactions.values());
  }

  @Override public List<Transaction> findAllBy(TransactionType transactionType) {
    return Lists.newArrayList(transactions.get(transactionType));
  }

  @Override public void save(Transaction transaction) {
    transactions.put(transaction.getTransactionType(), transaction);
  }
}
