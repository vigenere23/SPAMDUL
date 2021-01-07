package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.billing.entities.exceptions.InvoiceAlreadyPaidException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

  private final InvoiceId invoiceId;
  private final LocalDateTime createdAt;
  private LocalDateTime paidAt;
  private final List<Priceable> items = new ArrayList<>();

  public Invoice(InvoiceId invoiceId, LocalDateTime createdAt) {
    this.invoiceId = invoiceId;
    this.createdAt = createdAt;
  }

  public void pay() {
    if (paidAt != null) {
      throw new InvoiceAlreadyPaidException(invoiceId);
    }

    // TODO use payment service

    paidAt = LocalDateTime.now();
  }

  public void addItem(Priceable item) {
    items.add(item);
  }

  public Amount getTotal() {
    return items.stream()
                .map(Priceable::getPrice)
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
}
