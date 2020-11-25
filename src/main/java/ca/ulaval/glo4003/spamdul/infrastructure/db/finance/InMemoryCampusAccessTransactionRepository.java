package ca.ulaval.glo4003.spamdul.infrastructure.db.finance;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCampusAccessTransactionRepository implements CampusAccessTransactionRepository {

  private final Map<CarType, List<Transaction>> transactionsByCarType = new HashMap<>();

  @Override
  public List<Transaction> findAll() {
    return transactionsByCarType.values().stream().reduce((allTransactions, transactionList) -> {
      allTransactions.addAll(transactionList);
      return allTransactions;
    }).orElse(new ArrayList<>());
  }

  @Override
  public List<Transaction> findAllBy(CarType carType) {
    return new ArrayList<>(transactionsByCarType.get(carType));
  }

  @Override
  public List<Transaction> findAllBy(CarType carType, TransactionFilter transactionFilter) {
    List<Transaction> transactions = new ArrayList<>(transactionsByCarType.get(carType));
    return transactionFilter.setData(transactions).getResults();
  }

  @Override
  public void save(Transaction transaction, CarType carType) {
    List<Transaction> transactions = transactionsByCarType.getOrDefault(carType, new ArrayList<>());
    transactions.add(transaction);
    transactionsByCarType.put(carType, transactions);
  }
}
