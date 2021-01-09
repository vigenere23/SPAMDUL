package ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAmountRepository;

public interface InfractionAmountPopulator {

  void populate(InfractionAmountRepository infractionAmountRepository);
}
