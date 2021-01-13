package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObservable;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceFactory {

  private final InvoiceIdFactory invoiceIdFactory;
  private final InvoicePaidObservable invoicePaidObservable;

  public InvoiceFactory(InvoiceIdFactory invoiceIdFactory,
                        InvoicePaidObservable invoicePaidObservable) {
    this.invoiceIdFactory = invoiceIdFactory;
    this.invoicePaidObservable = invoicePaidObservable;
  }

  public Invoice create(List<InvoiceItem> items) {
    Invoice invoice = new Invoice(invoiceIdFactory.create(), LocalDateTime.now(), invoicePaidObservable);
    items.forEach(invoice::addItem);

    return invoice;
  }
}
