package ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfosRepository;

public interface InfractionInfosPopulator {

  void populate(InfractionInfosRepository infractionInfosRepository);
}
