package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryCampusAccessRepository implements CampusAccessRepository {

  private Map<CampusAccessCode, CampusAccess> campusAccesses;
  private final Logger logger = Logger.getLogger(UserRepository.class.getName());

  public InMemoryCampusAccessRepository() {
    campusAccesses = new HashMap<>();
  }

  public CampusAccessCode save(CampusAccess campusAccess) {
    campusAccesses.put(campusAccess.getCampusAccessCode(), campusAccess);
    String loggingInfos = String.format("Saving campus access: %s", campusAccess.getCampusAccessCode().toString());
    logger.info(loggingInfos);

    return campusAccess.getCampusAccessCode();
  }

  public CampusAccess findById(CampusAccessCode campusAccessCode) {
    return campusAccesses.get(campusAccessCode);
  }
}
