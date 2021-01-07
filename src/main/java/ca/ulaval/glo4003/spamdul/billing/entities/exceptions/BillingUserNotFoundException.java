package ca.ulaval.glo4003.spamdul.billing.entities.exceptions;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class BillingUserNotFoundException extends RuntimeException {

  public BillingUserNotFoundException(AccountId accountId) {
    super(String.format("No billing user found with account id %s.", accountId));
  }
}
