package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.billing.entities.exceptions.InvoiceAlreadyPaidException;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidInfos;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Invoice {

  private final InvoiceId invoiceId;
  private final LocalDateTime createdAt;
  private final InvoicePaidObservable invoicePaidObservable;
  private LocalDateTime paidAt;
  private final Set<InvoiceItem> items = new HashSet<>();

  public Invoice(InvoiceId invoiceId,
                 LocalDateTime createdAt,
                 InvoicePaidObservable invoicePaidObservable) {
    this.invoiceId = invoiceId;
    this.createdAt = createdAt;
    this.invoicePaidObservable = invoicePaidObservable;
  }

  public void pay() {
    if (paidAt != null) {
      throw new InvoiceAlreadyPaidException(invoiceId);
    }

    // TODO use payment service

    paidAt = LocalDateTime.now();

    invoicePaidObservable.notify(new InvoicePaidInfos(invoiceId, paidAt, getTotal()));
  }

  public void addItem(InvoiceItem item) {
    items.add(item);
  }

  public Amount getTotal() {
    return items.stream()
                .map(InvoiceItem::getPrice)
                .reduce(Amount::add)
                .orElse(Amount.valueOf(0));
  }

  public InvoiceId getId() {
    return invoiceId;
  }

  public InvoiceStatus getStatus() {
    return paidAt == null ? InvoiceStatus.UNPAID : InvoiceStatus.PAID;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getPaidAt() {
    return paidAt;
  }

  public Set<InvoiceItem> getItems() {
    return new HashSet<>(items);
  }
}
