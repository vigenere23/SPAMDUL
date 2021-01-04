package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public interface InfractionAmountRepository {

  Amount findBy(InfractionType infractionType);

  void save(InfractionType infractionType, Amount amount);
}
