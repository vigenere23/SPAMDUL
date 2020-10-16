package ca.ulaval.glo4003.spamdul.entity.transactions;

public class PassTransaction extends Transaction {

  private double amount;

  public PassTransaction(double amount) {
    this.amount = amount;
  }

  public double getAmount() {
    return amount;
  }
}
