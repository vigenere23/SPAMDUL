package ca.ulaval.glo4003.spamdul.invoice.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class Invoice implements Priceable {

  private final InvoiceId invoiceId;
  private final List<InvoiceItem> items = new ArrayList<>();

  public Invoice(InvoiceId invoiceId) {
    this.invoiceId = invoiceId;
  }

  public void checkout() {
    items.forEach(InvoiceItem::checkout);
  }

  public void addItem(InvoiceItem item) {
    items.add(item);
  }

  @Override
  public Amount getPrice() {
    return items.stream()
                .map(InvoiceItem::getPrice)
                .reduce(Amount::add)
                .orElse(Amount.valueOf(0));
  }

  public InvoiceId getId() {
    return invoiceId;
  }
}
