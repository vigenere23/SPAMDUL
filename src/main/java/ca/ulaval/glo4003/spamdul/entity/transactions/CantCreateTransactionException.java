package ca.ulaval.glo4003.spamdul.entity.transactions;

public class CantCreateTransactionException extends RuntimeException {

  public CantCreateTransactionException(String message) {
    super(message);
  }
}
