package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class TransactionFactory {

  public Transaction create(TransactionDto dto) {
    //TODO a retester
    LocalDateTime createdAt = LocalDateTime.now();

    if (dto.transactionType.equals(TransactionType.CAMPUS_ACCESS)) {
      if (dto.carType == null) {
        throw new CantCreateCampusAccessTransactionWithoutCarTypeException(
            "A campus access transaction must contain a valid car type");
      }
      return new CampusAccessTransaction(Amount.valueOf(dto.amount), createdAt, dto.carType);
    }  else {
      return new Transaction(Amount.valueOf(dto.amount), createdAt, dto.transactionType);
    }
  }
}
