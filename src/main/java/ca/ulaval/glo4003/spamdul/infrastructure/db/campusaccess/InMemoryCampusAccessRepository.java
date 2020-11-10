package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class InMemoryCampusAccessRepository implements CampusAccessRepository {

  private static final Map<CampusAccessCode, CampusAccess> campusAccesses = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryCampusAccessRepository.class.getName());

  public CampusAccessCode save(CampusAccess campusAccess) {
    campusAccesses.put(campusAccess.getCampusAccessCode(), campusAccess);
    String loggingInfos = String.format("Saving campus access: %s", campusAccess.getCampusAccessCode().toString());
    logger.info(loggingInfos);

    return campusAccess.getCampusAccessCode();
  }

  public CampusAccess findBy(CampusAccessCode campusAccessCode) {
    CampusAccess campusAccess = campusAccesses.get(campusAccessCode);

    if (campusAccess == null) {
      throw new CampusAccessNotFoundException(String.format("No campus access with id %s",
                                                            campusAccessCode.toString()));
    }

    return campusAccess;
  }

  public CampusAccess findBy(LicensePlate licensePlate) {
    for (Entry<CampusAccessCode, CampusAccess> entry : campusAccesses.entrySet()) {
      if (entry.getValue().validateAssociatedLicensePlate(licensePlate)) {
        return entry.getValue();
      }
    }

    throw new CampusAccessNotFoundException(String.format("No campus access with license plate %s",
                                                          licensePlate.toString()));
  }

  public void clear() {
    campusAccesses.clear();
  }
}
