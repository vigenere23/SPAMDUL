package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceFactory {

  private final InvoiceIdFactory invoiceIdFactory;

  public InvoiceFactory(InvoiceIdFactory invoiceIdFactory) {
    this.invoiceIdFactory = invoiceIdFactory;
  }

  public Invoice create(List<Priceable> items) {
    Invoice invoice = new Invoice(invoiceIdFactory.create(), LocalDateTime.now());
    items.forEach(invoice::addItem);

    return invoice;
  }
}
