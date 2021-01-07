package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;
import java.util.stream.Collectors;

public interface Priceable {

  Amount getPrice();

  String getName();

  static <T> List<Priceable> fromList(List<T> items) {
    return items.stream().map(item -> (Priceable) item).collect(Collectors.toList());
  }
}
