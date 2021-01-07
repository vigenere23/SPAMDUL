package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InvoiceItem {

  private final Amount price;
  private final String name;
  private final int quantity;

  public InvoiceItem(Amount price, String name, int quantity) {
    this.price = price;
    this.name = name;
    this.quantity = quantity;
  }

  public Amount getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }
}
