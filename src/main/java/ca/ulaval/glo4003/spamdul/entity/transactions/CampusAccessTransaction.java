package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;

public class CampusAccessTransaction extends Transaction {

  private double amount;
  private CarType carType;

  public CampusAccessTransaction(double amount, CarType carType) {
    this.amount = amount;
    this.carType = carType;
  }

  public double getAmount() {
    return amount;
  }

  public CarType getCarType() {
    return carType;
  }
}
