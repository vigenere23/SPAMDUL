package ca.ulaval.glo4003.spamdul.invoice.entities;

import java.util.List;

public class InvoiceCreator {

  private final InvoiceFactory invoiceFactory;
  private final InvoiceRepository invoiceRepository;

  public InvoiceCreator(InvoiceFactory invoiceFactory,
                        InvoiceRepository invoiceRepository) {
    this.invoiceFactory = invoiceFactory;
    this.invoiceRepository = invoiceRepository;
  }

  public InvoiceId createInvoice(List<Priceable> items) {
    Invoice invoice = invoiceFactory.create(items);
    invoiceRepository.save(invoice);

    return invoice.getId();
  }
}
