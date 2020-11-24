package ca.ulaval.glo4003.spamdul.entity.finance.exceptions;

public abstract class FinancialException extends RuntimeException {

  public FinancialException(String message) {
    super(message);
  }
}
