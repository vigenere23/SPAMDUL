package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InvoiceItem {

  private final Amount unitPrice;
  private final String name;
  private final int quantity;

  public InvoiceItem(Amount unitPrice, String name, int quantity) {
    this.unitPrice = unitPrice;
    this.name = name;
    this.quantity = quantity;
  }

  public Amount getPrice() {
    return unitPrice.multiply(quantity);
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }
}
