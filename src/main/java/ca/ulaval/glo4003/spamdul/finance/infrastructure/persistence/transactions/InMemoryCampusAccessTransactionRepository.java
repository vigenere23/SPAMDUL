package ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.transactions;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCampusAccessTransactionRepository implements CampusAccessTransactionRepository {

  private final Map<CarType, List<Transaction>> transactionsByCarType = new HashMap<>();

  @Override
  public List<Transaction> findAll() {
    List<Transaction> allTransactions = new ArrayList<>();
    transactionsByCarType.values().forEach(allTransactions::addAll);
    return allTransactions;
  }

  @Override
  public List<Transaction> findAllBy(CarType carType) {
    return new ArrayList<>(transactionsByCarType.getOrDefault(carType, new ArrayList<>()));
  }

  @Override
  public List<Transaction> findAllBy(CarType carType, TransactionFilter transactionFilter) {
    return transactionFilter.setData(findAllBy(carType)).getResults();
  }

  @Override
  public void save(Transaction transaction, CarType carType) {
    List<Transaction> transactions = findAllBy(carType);
    transactions.add(transaction);
    transactionsByCarType.put(carType, transactions);
  }
}
