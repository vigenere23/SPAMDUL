package ca.ulaval.glo4003.spamdul.entity.transactions;

public class InfractionTransaction extends Transaction{

  private double amount;

  public InfractionTransaction(double amount) {
    this.amount = amount;
  }

  public double getAmount() {
    return amount;
  }
}
