package ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions;

public class InvalidInitiativeCodeException extends InvalidInitiativeException {

  public InvalidInitiativeCodeException() {
    super("The initiative code is either invalid or reserved. Please try another one.");
  }
}
