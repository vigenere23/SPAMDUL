package ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions;

public class InsufficientFundsException extends InvalidInitiativeException {

  public InsufficientFundsException() {
    super("Insufficient funds");
  }
}
