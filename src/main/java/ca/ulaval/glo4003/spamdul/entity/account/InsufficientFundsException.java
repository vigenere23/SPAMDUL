package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeException;

public class InsufficientFundsException extends InvalidInitiativeException {

  protected InsufficientFundsException(String message) {
    super(message);
  }
}
