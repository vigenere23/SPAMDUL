package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAmountRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class InfractionAmountRepositoryInMemory implements InfractionAmountRepository {

  private final Map<InfractionType, Amount> infractionAmounts = new HashMap<>();

  @Override public Amount findBy(InfractionType infractionType) {
    Amount amount = infractionAmounts.get(infractionType);
    if (amount == null) {
      return Amount.valueOf(0);
    }

    return amount;
  }

  @Override public void save(InfractionType infractionType, Amount amount) {
    infractionAmounts.put(infractionType, amount);
  }
}
