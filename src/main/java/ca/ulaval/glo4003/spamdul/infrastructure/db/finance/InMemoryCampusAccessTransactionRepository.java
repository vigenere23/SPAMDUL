package ca.ulaval.glo4003.spamdul.infrastructure.db.finance;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryCampusAccessTransactionRepository implements CampusAccessTransactionRepository {

  private final Map<CarType, Transaction> transactions = new HashMap<>();

  @Override public List<Transaction> findAll() {
    return Lists.newArrayList(transactions.values());
  }

  @Override public List<Transaction> findAllBy(CarType carType) {
    return Lists.newArrayList(transactions.get(carType));
  }

  @Override public void save(Transaction transaction, CarType carType) {
    transactions.put(carType, transaction);
  }
}
