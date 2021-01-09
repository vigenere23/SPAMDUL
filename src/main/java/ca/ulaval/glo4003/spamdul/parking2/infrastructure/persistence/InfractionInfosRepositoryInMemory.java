package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfosRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionType;
import java.util.HashMap;
import java.util.Map;

public class InfractionInfosRepositoryInMemory implements InfractionInfosRepository {

  private final Map<InfractionType, InfractionInfos> infractionInfosByType = new HashMap<>();

  @Override
  public InfractionInfos findBy(InfractionType infractionType) {
    return infractionInfosByType.get(infractionType);
  }

  @Override
  public void save(InfractionInfos infractionInfos) {
    infractionInfosByType.put(infractionInfos.getInfractionType(), infractionInfos);
  }
}
