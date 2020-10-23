package ca.ulaval.glo4003.spamdul.entity.transactions;


import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class InfractionTransaction extends Transaction {

  public InfractionTransaction(Amount amount, LocalDateTime createdAt) {
    super(amount, createdAt);
  }
}
