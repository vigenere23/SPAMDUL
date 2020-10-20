package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public abstract class Transaction {

  private final Amount amount;

  protected Transaction(Amount amount) {
    this.amount = amount;
  }

  public Amount getAmount() {
    return this.amount;
  }
}
