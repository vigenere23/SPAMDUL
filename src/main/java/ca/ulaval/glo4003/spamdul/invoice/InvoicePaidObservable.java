package ca.ulaval.glo4003.spamdul.invoice;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoicePaidObserver;
import java.util.ArrayList;
import java.util.List;

public class InvoicePaidObservable {

  private final List<InvoicePaidObserver> observers = new ArrayList<>();

  public void register(InvoicePaidObserver observer) {
    observers.add(observer);
  }

  public void unregister(InvoicePaidObserver observer) {
    observers.remove(observer);
  }

  public void notify(InvoiceId invoiceId) {
    observers.forEach(observer -> observer.handleInvoicePaid(invoiceId));
  }
}
