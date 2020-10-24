package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public abstract class Transaction {

  private final Amount amount;
  private final LocalDateTime createdAt;

  protected Transaction(Amount amount, LocalDateTime createdAt) {
    this.amount = amount;
    this.createdAt = createdAt;
  }

  public Amount getAmount() {
    return this.amount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
