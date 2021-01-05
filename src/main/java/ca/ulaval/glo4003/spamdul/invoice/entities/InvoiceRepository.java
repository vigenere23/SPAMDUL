package ca.ulaval.glo4003.spamdul.invoice.entities;

public interface InvoiceRepository {

  Invoice findBy(InvoiceId invoiceId);

  void save(Invoice invoice);
}
