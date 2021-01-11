package ca.ulaval.glo4003.spamdul.billing.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserverRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoicePaidObserverRepositoryInMemory implements InvoicePaidObserverRepository {

  private final Map<InvoiceId, List<InvoicePaidObserver>> callbacksByInvoiceId = new HashMap<>();

  @Override public List<InvoicePaidObserver> findAllBy(InvoiceId invoiceId) {
    return callbacksByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
  }

  @Override public void add(InvoiceId invoiceId, InvoicePaidObserver observer) {
    List<InvoicePaidObserver> callbacks = callbacksByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    callbacks.add(observer);
    callbacksByInvoiceId.put(invoiceId, callbacks);
  }

  @Override public void remove(InvoiceId invoiceId, InvoicePaidObserver observer) {
    List<InvoicePaidObserver> callbacks = callbacksByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    callbacks.remove(observer);
    callbacksByInvoiceId.put(invoiceId, callbacks);
  }
}
