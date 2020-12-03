package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Logger;

public class InMemoryCampusAccessRepository implements CampusAccessRepository, PassRepository {

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
      throw new CampusAccessNotFoundException();
    }

    return campusAccess;
  }

  public List<CampusAccess> findBy(LicensePlate licensePlate) {
    List<CampusAccess> campusAccessesAssociatedWithLicensePlate = new ArrayList<>();

    for (Entry<CampusAccessCode, CampusAccess> entry : campusAccesses.entrySet()) {
      if (entry.getValue().validateAssociatedLicensePlate(licensePlate)) {
        campusAccessesAssociatedWithLicensePlate.add(entry.getValue());
      }
    }

    if (campusAccessesAssociatedWithLicensePlate.isEmpty()) {
      throw new CampusAccessNotFoundException();
    }

    return campusAccessesAssociatedWithLicensePlate;
  }

  public Pass findByPassCode(PassCode passCode) {
    Optional<Pass> optionalPass = campusAccesses.values()
                                                .stream()
                                                .filter(CampusAccess::hasAssociatedPass)
                                                .map(CampusAccess::getAssociatedPass)
                                                .filter(pass -> pass.getPassCode().equals(passCode))
                                                .findFirst();
    if (optionalPass.isPresent()) {
      return optionalPass.get();
    } else {
      throw new PassNotFoundException(passCode.toString());
    }
  }

  public void deleteAll() {
    campusAccesses.clear();
  }
}
