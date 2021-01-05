package ca.ulaval.glo4003.spamdul.invoice.entities;

public class InvoiceFactory {

  private final InvoiceIdFactory invoiceIdFactory;

  public InvoiceFactory(InvoiceIdFactory invoiceIdFactory) {
    this.invoiceIdFactory = invoiceIdFactory;
  }

  public Invoice create(InvoiceItem... items) {
    Invoice invoice = new Invoice(invoiceIdFactory.create());
    
    for (InvoiceItem item : items) {
      invoice.addItem(item);
    }

    return invoice;
  }
}
