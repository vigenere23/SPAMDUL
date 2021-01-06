package ca.ulaval.glo4003.spamdul.invoice.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.function.Consumer;

public class InvoiceItem implements Priceable {

  private final Priceable item;
  private final Consumer<Priceable> callback;

  public InvoiceItem(Priceable item,
                     Consumer<Priceable> callback) {
    this.item = item;
    this.callback = callback;
  }

  public void handlePaid() {
    callback.accept(item);
  }

  @Override
  public Amount getPrice() {
    return item.getPrice();
  }
}
