package ca.ulaval.glo4003.spamdul.entity.finance;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;

public class Transaction {

  private final Amount amount;
  private final LocalDateTime createdAt;
  private final TransactionType transactionType;

  public Transaction(Amount amount, LocalDateTime createdAt, TransactionType transactionType) {
    this.amount = amount;
    this.createdAt = createdAt;
    this.transactionType = transactionType;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public Amount getAmount() {
    return this.amount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
