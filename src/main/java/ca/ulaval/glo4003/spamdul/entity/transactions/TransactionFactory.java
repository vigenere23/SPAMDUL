package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;

public class TransactionFactory {

  public Transaction create(TransactionDto dto) {
    LocalDateTime createdAt = LocalDateTime.now();

    if (dto.transactionType.equals(TransactionType.CAMPUS_ACCESS)) {
      if (dto.carType == null) {
        throw new CantCreateCampusAccessTransactionWithoutCarTypeException(
            "A campus access transaction must contain a valid car type");
      }
      return new CampusAccessTransaction(Amount.valueOf(dto.amount), createdAt, dto.carType);
    } else if (dto.transactionType.equals(TransactionType.PASS)) {
      return new PassTransaction(Amount.valueOf(dto.amount), createdAt);
    } else if (dto.transactionType.equals(TransactionType.INFRACTION)) {
      return new InfractionTransaction(Amount.valueOf(dto.amount), createdAt);
    } else if (dto.transactionType.equals(TransactionType.INITIATIVE)) {
      return new InitiativeTransaction(Amount.valueOf(dto.amount * -1), createdAt); //TODO pas tester
    } else if (dto.transactionType.equals(TransactionType.CARBON_CREDIT)) {
      return new CarbonCreditTransaction(Amount.valueOf(dto.amount * -1), createdAt); // TODO pas tester
    } else {
      throw new CantCreateTransactionException("transaction type must be valid");
    }
  }
}
