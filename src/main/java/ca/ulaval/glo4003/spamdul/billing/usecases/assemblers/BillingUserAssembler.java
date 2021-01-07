package ca.ulaval.glo4003.spamdul.billing.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUser;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.BillingUserDto;

public class BillingUserAssembler {

  private final InvoiceAssembler invoiceAssembler;

  public BillingUserAssembler(InvoiceAssembler invoiceAssembler) {
    this.invoiceAssembler = invoiceAssembler;
  }

  public BillingUserDto toDto(BillingUser billingUser) {
    BillingUserDto dto = new BillingUserDto();
    dto.accountId = billingUser.getAccountId();
    dto.invoices = invoiceAssembler.toDtos(billingUser.getInvoices());

    return dto;
  }
}
