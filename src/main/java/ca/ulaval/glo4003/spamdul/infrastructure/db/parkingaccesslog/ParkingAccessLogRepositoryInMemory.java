package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import jersey.repackaged.com.google.common.collect.Lists;

public class ParkingAccessLogRepositoryInMemory implements ParkingAccessLogRepository {

  private static final Map<ParkingAccessLogId, ParkingAccessLog> parkingAccessLogsById = new HashMap<>();
  private final ParkingAccessLogFactory parkingAccessLogFactory;
  private final Logger logger = Logger.getLogger(getClass().getName()); // TODO remove once not needed

  public ParkingAccessLogRepositoryInMemory(ParkingAccessLogFactory parkingAccessLogFactory) {
    this.parkingAccessLogFactory = parkingAccessLogFactory;
  }

  @Override
  public List<ParkingAccessLog> findAll() {
    return Lists.newArrayList(parkingAccessLogsById.values());
  }

  @Override
  public void save(ParkingAccessLog parkingAccessLog) {
    parkingAccessLogsById.put(parkingAccessLog.getId(), parkingAccessLog);
  }

  @Override public void handleAccessGrantedWithCampusAccess(CampusAccess campusAccess, LocalDate accessDate) {
    // TODO need parking zone!
    // TODO could add carId to ParkingAccessLog fields
    // save(parkingAccessLogFactory.create(parkingZone, accessDate));
    logger.info(
        "creating parking access log from access granted from campus access: " + campusAccess.getCampusAccessCode());
  }
}
