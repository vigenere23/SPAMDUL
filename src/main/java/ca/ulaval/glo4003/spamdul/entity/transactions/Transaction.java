package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class Transaction {

  private final Amount amount;
  private final LocalDateTime createdAt;
  private TransactionType transactionType;

  public Transaction(Amount amount, LocalDateTime createdAt, TransactionType transactionType) {
    this.amount = amount;
    this.createdAt = createdAt;
    this.transactionType = transactionType;
  }

  public Transaction(Transaction transaction, Amount newAmount) {
    this.amount = newAmount;
    this.createdAt = transaction.createdAt;
    this.transactionType = transaction.transactionType;
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
