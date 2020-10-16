package ca.ulaval.glo4003.spamdul.entity.transactions;

public class TransactionFactory {

  public Transaction create(TransactionDto dto) {
    if (dto.transactionType.equals(TransactionType.CAMPUS_ACCESS)) {
      if (dto.carType == null) {
        throw new CantCreateCampusAccessTransactionWithoutCarTypeException(
            "A campus access transaction must contain a valid car type");
      }
      return new CampusAccessTransaction(dto.amount, dto.carType);
    } else if (dto.transactionType.equals(TransactionType.PASS)) {
      return new PassTransaction(dto.amount);
    } else if (dto.transactionType.equals(TransactionType.INFRACTION)){
      return new InfractionTransaction(dto.amount);
    } else {
      throw new CantCreateTransactionException("transaction type must be valid");
    }
  }
}
