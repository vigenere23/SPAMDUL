package ca.ulaval.glo4003.spamdul.entity.finance.exceptions;

public abstract class FinanceException extends RuntimeException {

  public FinanceException(String message) {
    super(message);
  }
}
