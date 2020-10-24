package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class PassTransaction extends Transaction {

  public PassTransaction(Amount amount, LocalDateTime createdAt) {
    super(amount, createdAt);
  }
}
