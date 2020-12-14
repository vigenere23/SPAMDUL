package ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions;

public class InsufficientFundsException extends InvalidInitiativeException {

  public String getError() {
   return "INVALID_INITIATIVE_AMOUNT";
  }

  public String getDescription() {
    return "Insufficient funds";
  }
}
