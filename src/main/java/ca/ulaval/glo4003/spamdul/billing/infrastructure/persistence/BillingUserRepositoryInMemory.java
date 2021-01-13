package ca.ulaval.glo4003.spamdul.billing.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.exceptions.BillingUserNotFoundException;
import ca.ulaval.glo4003.spamdul.billing.entities.exceptions.InvoiceNotFoundException;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUser;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BillingUserRepositoryInMemory implements BillingUserRepository {

  private final Map<AccountId, BillingUser> users = new HashMap<>();

  @Override
  public BillingUser findBy(AccountId accountId) {
    return Optional.ofNullable(users.get(accountId)).orElseThrow(() -> new BillingUserNotFoundException(accountId));
  }

  @Override public BillingUser findBy(InvoiceId invoiceId) {
    return users.values()
                .stream()
                .filter(user -> user.hasInvoice(invoiceId))
                .findFirst()
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
  }

  @Override
  public void save(BillingUser billingUser) {
    users.put(billingUser.getAccountId(), billingUser);
  }
}
