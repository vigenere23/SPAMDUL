package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryCampusAccessRepository implements CampusAccessRepository {

  private static final Map<CampusAccessCode, CampusAccess> campusAccesses = new HashMap<>();
  private final Logger logger = Logger.getLogger(CampusAccessRepository.class.getName());

  public CampusAccessCode save(CampusAccess campusAccess) {
    campusAccesses.put(campusAccess.getCampusAccessCode(), campusAccess);
    String loggingInfos = String.format("Saving campus access: %s", campusAccess.getCampusAccessCode().toString());
    logger.info(loggingInfos);

    return campusAccess.getCampusAccessCode();
  }

  public CampusAccess findById(CampusAccessCode campusAccessCode) {
    CampusAccess campusAccess = campusAccesses.get(campusAccessCode);

    if (campusAccess == null) {
      throw new CampusAccessNotFoundException();
    }

    return campusAccess;
  }
}
