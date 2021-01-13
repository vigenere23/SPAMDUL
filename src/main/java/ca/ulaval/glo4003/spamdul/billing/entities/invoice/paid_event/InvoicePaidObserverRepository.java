package ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import java.util.List;

public interface InvoicePaidObserverRepository {

  List<InvoicePaidObserver> findAllBy(InvoiceId invoiceId);

  void add(InvoiceId invoiceId, InvoicePaidObserver observer);

  void remove(InvoiceId invoiceId, InvoicePaidObserver observer);
}
