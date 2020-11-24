package ca.ulaval.glo4003.spamdul.entity.finance;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.util.List;

public interface CampusAccessTransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAllBy(CarType carType);

  void save(Transaction transaction, CarType carType);
}
