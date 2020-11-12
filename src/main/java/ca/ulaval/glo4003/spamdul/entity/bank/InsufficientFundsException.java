package ca.ulaval.glo4003.spamdul.entity.bank;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeException;

public class InsufficientFundsException extends InvalidInitiativeException {

  protected InsufficientFundsException(String message) {
    super(message);
  }
}
