package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUser;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserRepository;
import java.util.List;

public class InvoiceCreator {

  private final InvoiceFactory invoiceFactory;
  private final BillingUserRepository billingUserRepository;

  public InvoiceCreator(InvoiceFactory invoiceFactory,
                        BillingUserRepository billingUserRepository) {
    this.invoiceFactory = invoiceFactory;
    this.billingUserRepository = billingUserRepository;
  }

  public InvoiceId createInvoice(AccountId accountId, List<Priceable> items) {
    BillingUser billingUser = billingUserRepository.findBy(accountId);
    Invoice invoice = invoiceFactory.create(items);
    billingUser.addInvoice(invoice);
    billingUserRepository.save(billingUser);

    return invoice.getId();
  }
}
