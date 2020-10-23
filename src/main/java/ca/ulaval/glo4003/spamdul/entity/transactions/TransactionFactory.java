package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class TransactionFactory {

  public Transaction create(TransactionDto dto) {
    if (dto.createdAt == null) {
      dto.createdAt = LocalDateTime.now();
    }

    if (dto.transactionType.equals(TransactionType.CAMPUS_ACCESS)) {
      if (dto.carType == null) {
        throw new CantCreateCampusAccessTransactionWithoutCarTypeException(
            "A campus access transaction must contain a valid car type");
      }
      return new CampusAccessTransaction(Amount.valueOf(dto.amount), dto.createdAt, dto.carType);
    } else if (dto.transactionType.equals(TransactionType.PASS)) {
      return new PassTransaction(Amount.valueOf(dto.amount), dto.createdAt);
    } else if (dto.transactionType.equals(TransactionType.INFRACTION)) {
      return new InfractionTransaction(Amount.valueOf(dto.amount), dto.createdAt);
    } else {
      throw new CantCreateTransactionException("transaction type must be valid");
    }
  }
}
