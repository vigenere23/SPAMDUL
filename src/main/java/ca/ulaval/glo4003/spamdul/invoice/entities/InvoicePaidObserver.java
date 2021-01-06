package ca.ulaval.glo4003.spamdul.invoice.entities;

public interface InvoicePaidObserver {

  void handleInvoicePaid(InvoiceId invoiceId);
}
