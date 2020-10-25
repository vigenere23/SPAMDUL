package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;
import java.util.Objects;

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

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Objects.equals(amount, that.amount) &&
        Objects.equals(createdAt, that.createdAt) &&
        transactionType == that.transactionType;
  }

  public int hashCode() {
    return Objects.hash(amount, createdAt, transactionType);
  }
}
