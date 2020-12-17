package ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions;

public class InvalidInitiativeCodeException extends InvalidInitiativeException {

  public String getError() {
    return "INVALID_INITIATIVE_CODE";
  }

  public String getDescription() {
    return "The initiative code is either invalid or reserved. Please try another one.";
  }
}
