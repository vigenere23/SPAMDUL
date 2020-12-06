package ca.ulaval.glo4003.spamdul.entity.finance.transaction;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import java.util.List;

public interface CampusAccessTransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAllBy(CarType carType);

  List<Transaction> findAllBy(CarType carType, TransactionFilter transactionFilter);

  void save(Transaction transaction, CarType carType);
}
