package ca.ulaval.glo4003.spamdul.billing.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;

public interface BillingUserRepository {

  BillingUser findBy(AccountId accountId);

  BillingUser findBy(InvoiceId accountId);

  void save(BillingUser billingUser);
}
