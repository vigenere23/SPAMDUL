package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.InMemoryUserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryInfractionRepository implements InfractionRepository {

  private static final Map<InfractionId, Infraction> infractionMap = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryUserRepository.class.getName());


  @Override public InfractionId save(Infraction infraction) {
    infractionMap.put(infraction.getInfractionId(), infraction);
    String loggingInfos = String.format("Saving infraction: %s", infraction.getInfractionId().toString());
    logger.info(loggingInfos);

    return infraction.getInfractionId();
  }

  @Override public Infraction findBy(InfractionId infractionId) {
    Infraction infraction = infractionMap.get(infractionId);

    if (infraction == null) {
      throw new InfractionNotFoundException();
    }

    return infraction;
  }
}
