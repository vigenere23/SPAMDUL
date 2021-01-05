package ca.ulaval.glo4003.spamdul.invoice.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public interface Priceable {

  Amount getPrice();
}
