package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.util.List;

public interface TransactionRepository {

  void save(Transaction transaction);

  List<Transaction> findAllByType(TransactionType transactionType);

  List<Transaction> findAllByCarType(CarType carType);
}
