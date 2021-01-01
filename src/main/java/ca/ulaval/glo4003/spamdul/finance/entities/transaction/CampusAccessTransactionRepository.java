package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import java.util.List;

public interface CampusAccessTransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAllBy(CarType carType);

  List<Transaction> findAllBy(CarType carType, TransactionFilter transactionFilter);

  void save(Transaction transaction, CarType carType);
}
