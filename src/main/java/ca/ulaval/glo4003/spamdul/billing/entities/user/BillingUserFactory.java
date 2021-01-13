package ca.ulaval.glo4003.spamdul.billing.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class BillingUserFactory {

  public BillingUser create(AccountId accountId) {
    return new BillingUser(accountId);
  }
}
