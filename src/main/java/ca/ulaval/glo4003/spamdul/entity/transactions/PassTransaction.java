package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;

public class PassTransaction extends Transaction{

  private double amount;
  private ParkingZone parkingZone;

  public PassTransaction(double amount, ParkingZone parkingZone) {
    this.amount = amount;
    this.parkingZone = parkingZone;
  }

  public double getAmount() {
    return amount;
  }
}
