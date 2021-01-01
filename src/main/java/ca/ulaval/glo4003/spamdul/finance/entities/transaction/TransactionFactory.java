package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public class TransactionFactory {

  public Transaction create(TransactionType transactionType, Amount amount) {
    LocalDateTime createdAt = LocalDateTime.now();

    return new Transaction(amount, createdAt, transactionType);
  }
}
