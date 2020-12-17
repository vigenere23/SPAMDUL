package ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions;

public class InvalidInitiativeAmountException extends InvalidInitiativeException {

  public String getError() {
    return "INVALID_INITIATIVE_AMOUNT";
  }

  public String getDescription() {
    return "Invalid initiative amount";
  }
}
