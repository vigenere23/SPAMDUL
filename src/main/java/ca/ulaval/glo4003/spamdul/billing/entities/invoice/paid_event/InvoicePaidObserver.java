package ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event;

public interface InvoicePaidObserver {

  void handleInvoicePaid(InvoicePaidInfos invoicePaidInfos);
}
