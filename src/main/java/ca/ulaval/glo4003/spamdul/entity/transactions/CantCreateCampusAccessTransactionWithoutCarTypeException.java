package ca.ulaval.glo4003.spamdul.entity.transactions;

public class CantCreateCampusAccessTransactionWithoutCarTypeException extends RuntimeException {

  public CantCreateCampusAccessTransactionWithoutCarTypeException(String message) {
    super(message);
  }
}
