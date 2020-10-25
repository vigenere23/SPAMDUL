package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class CampusAccessTransaction extends Transaction {

  private final CarType carType;

  public CampusAccessTransaction(Amount amount, LocalDateTime createdAt, CarType carType) {
    super(amount, createdAt, TransactionType.CAMPUS_ACCESS);
    this.carType = carType;
  }

  public CarType getCarType() {
    return carType;
  }
}
