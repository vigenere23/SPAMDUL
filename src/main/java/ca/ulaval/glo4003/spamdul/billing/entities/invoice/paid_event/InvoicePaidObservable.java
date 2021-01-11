package ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import java.util.List;

public class InvoicePaidObservable {

  private final InvoicePaidObserverRepository invoicePaidObserverRepository;

  public InvoicePaidObservable(InvoicePaidObserverRepository invoicePaidObserverRepository) {
    this.invoicePaidObserverRepository = invoicePaidObserverRepository;
  }

  public void register(InvoiceId invoiceId, InvoicePaidObserver observer) {
    invoicePaidObserverRepository.add(invoiceId, observer);
  }

  public void unregister(InvoiceId invoiceId, InvoicePaidObserver observer) {
    invoicePaidObserverRepository.remove(invoiceId, observer);
  }

  public void notify(InvoicePaidInfos infos) {
    List<InvoicePaidObserver> observers = invoicePaidObserverRepository.findAllBy(infos.getInvoiceId());
    observers.forEach(observer -> observer.handleInvoicePaid(infos));
  }
}
