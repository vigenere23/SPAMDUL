package ca.ulaval.glo4003.spamdul.billing.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.Invoice;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUser;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserRepository;
import ca.ulaval.glo4003.spamdul.billing.usecases.assemblers.BillingUserAssembler;
import ca.ulaval.glo4003.spamdul.billing.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.BillingUserDto;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceDto;

public class BillingUseCase {

  private final BillingUserRepository billingUserRepository;
  private final BillingUserAssembler billingUserAssembler;
  private final InvoiceAssembler invoiceAssembler;
  private final InvoicePaidObservable invoicePaidObservable;

  public BillingUseCase(BillingUserRepository billingUserRepository,
                        BillingUserAssembler billingUserAssembler,
                        InvoiceAssembler invoiceAssembler,
                        InvoicePaidObservable invoicePaidObservable) {
    this.billingUserRepository = billingUserRepository;
    this.billingUserAssembler = billingUserAssembler;
    this.invoiceAssembler = invoiceAssembler;
    this.invoicePaidObservable = invoicePaidObservable;
  }

  public BillingUserDto getUser(AccountId accountId) {
    BillingUser billingUser = billingUserRepository.findBy(accountId);

    return billingUserAssembler.toDto(billingUser);
  }

  public InvoiceDto payInvoice(InvoiceId invoiceId/*, payment infos (credit card)*/) {
    BillingUser billingUser = billingUserRepository.findBy(invoiceId);
    Invoice invoice = billingUser.payInvoice(invoiceId);
    billingUserRepository.save(billingUser);

    invoicePaidObservable.notify(invoiceId);

    return invoiceAssembler.toDto(invoice);
  }
}
