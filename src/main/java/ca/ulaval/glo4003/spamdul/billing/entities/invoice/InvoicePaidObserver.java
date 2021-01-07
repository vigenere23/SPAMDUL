package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

public interface InvoicePaidObserver {

  void handleInvoicePaid(InvoiceId invoiceId);
}
