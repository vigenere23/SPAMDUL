package ca.ulaval.glo4003.spamdul.invoice.entities;

import ca.ulaval.glo4003.spamdul.invoice.entities.exceptions.InvoiceAlreadyPaidException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

  private final InvoiceId invoiceId;
  private final LocalDateTime createdAt;
  private final List<InvoiceItem> items = new ArrayList<>();
  private boolean isPaid = false;

  public Invoice(InvoiceId invoiceId, LocalDateTime createdAt) {
    this.invoiceId = invoiceId;
    this.createdAt = createdAt;
  }

  public void pay() {
    if (isPaid) {
      throw new InvoiceAlreadyPaidException(invoiceId);
    }

    // TODO use payment service

    items.forEach(InvoiceItem::handlePaid);
    isPaid = true;
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

  public String getStatus() {
    // TODO use state with Unpaid, Paying and Paid
    return isPaid ? "paid" : "unpaid";
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
