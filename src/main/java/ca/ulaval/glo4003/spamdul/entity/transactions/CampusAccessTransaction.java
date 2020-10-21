package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class CampusAccessTransaction extends Transaction {

  private final CarType carType;

  public CampusAccessTransaction(Amount amount, CarType carType) {
    super(amount);
    this.carType = carType;
  }

  public CarType getCarType() {
    return carType;
  }
}
