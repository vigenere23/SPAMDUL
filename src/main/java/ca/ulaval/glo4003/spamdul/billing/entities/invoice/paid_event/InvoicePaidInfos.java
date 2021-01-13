package ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public class InvoicePaidInfos {

  private final InvoiceId invoiceId;
  private final LocalDateTime paidAt;
  private final Amount total;

  public InvoicePaidInfos(InvoiceId invoiceId,
                          LocalDateTime paidAt,
                          Amount total) {
    this.invoiceId = invoiceId;
    this.paidAt = paidAt;
    this.total = total;
  }

  public InvoiceId getInvoiceId() {
    return invoiceId;
  }

  public LocalDateTime getPaidAt() {
    return paidAt;
  }

  public Amount getTotal() {
    return total;
  }
}
