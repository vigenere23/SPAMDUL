package ca.ulaval.glo4003.spamdul.billing.entities.exceptions;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class InvoicePendingException extends RuntimeException {

  public InvoicePendingException(AccountId accountId) {
    super(String.format("An invoice is already pending for account %s.", accountId));
  }
}
