package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
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

    } else {
      return new Transaction(Amount.valueOf(dto.amount), createdAt, dto.transactionType);
    }
  }

  public Transaction create(Transaction transaction, Amount newAmount) {
    if (transaction.getTransactionType().equals(TransactionType.CAMPUS_ACCESS)) {
      return new CampusAccessTransaction(newAmount,
                                         transaction.getCreatedAt(),
                                         ((CampusAccessTransaction) transaction).getCarType());
    }

    return new Transaction(newAmount, transaction.getCreatedAt(), transaction.getTransactionType());
  }

  public Transaction create(TransactionType transactionType, Amount amount) {
    LocalDateTime createdAt = LocalDateTime.now();

    return new Transaction(amount, createdAt, transactionType);
  }
}
