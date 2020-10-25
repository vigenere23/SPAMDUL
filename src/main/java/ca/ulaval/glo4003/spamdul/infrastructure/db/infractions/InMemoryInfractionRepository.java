package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryInfractionRepository implements InfractionRepository {

  private static Map<InfractionId, Infraction> infractionMap = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryCampusAccessRepository.class.getName());


  public InfractionId save(Infraction infraction) {
    infractionMap.put(infraction.getInfractionId(), infraction);
    String loggingInfos = String.format("Saving infraction: %s", infraction.getInfractionId().toString());
    logger.info(loggingInfos);

    return infraction.getInfractionId();
  }

  public Infraction findBy(InfractionId infractionId) {
    Infraction infraction = infractionMap.get(infractionId);

    if (infraction == null) {
      throw new InfractionNotFoundException(String.format("No infraction with id: %s", infractionId));
    }

    return infraction;
  }
}
