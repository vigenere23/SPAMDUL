package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import java.util.List;
import java.util.stream.Collectors;

public interface Priceable {

  InvoiceItem getPricedItem();

  static <T> List<Priceable> fromList(List<T> items) {
    return items.stream().map(item -> (Priceable) item).collect(Collectors.toList());
  }
}
