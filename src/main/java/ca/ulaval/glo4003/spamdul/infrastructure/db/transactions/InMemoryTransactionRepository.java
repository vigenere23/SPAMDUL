package ca.ulaval.glo4003.spamdul.infrastructure.db.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.CampusAccessTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.InfractionTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.PassTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements TransactionRepository {

  private Map<TransactionType, List<Transaction>> transactions;

  public InMemoryTransactionRepository() {
    this.transactions = new HashMap<>();
    List<Transaction> campusAccessTransactions = new ArrayList<>();
    List<Transaction> passTransactions = new ArrayList<>();
    List<Transaction> infractionTransactions = new ArrayList<>();
    transactions.put(TransactionType.CAMPUS_ACCESS, campusAccessTransactions);
    transactions.put(TransactionType.PASS, passTransactions);
    transactions.put(TransactionType.INFRACTION, infractionTransactions);
  }

  public void save(Transaction transaction) {
    if (transaction instanceof CampusAccessTransaction) {
      save((CampusAccessTransaction) transaction);
    } else if (transaction instanceof PassTransaction) {
      save((PassTransaction) transaction);
    } else if (transaction instanceof InfractionTransaction) {
      save((InfractionTransaction) transaction);
    }
  }

  private void save(CampusAccessTransaction transaction) {
    transactions.get(TransactionType.CAMPUS_ACCESS).add(transaction);
  }

  private void save(PassTransaction transaction) {
    transactions.get(TransactionType.PASS).add(transaction);
  }

  private void save(InfractionTransaction transaction) {
    transactions.get(TransactionType.INFRACTION).add(transaction);
  }

  public List<Transaction> findAllBy(TransactionType transactionType) {
    return transactions.get(transactionType);
  }

  public List<Transaction> findAllBy(CarType carType) {
    List<Transaction> carTypeTransactions = new ArrayList<>();

    transactions.get(TransactionType.CAMPUS_ACCESS).forEach(transaction -> {
      CarType transactionCarType = ((CampusAccessTransaction) transaction).getCarType();
      if (transactionCarType == carType) {
        carTypeTransactions.add(transaction);
      }
    });

    return carTypeTransactions;
  }
}
