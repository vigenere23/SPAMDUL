package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class ParkingAccessLogRepositoryInMemory implements ParkingAccessLogRepository {

  private Map<String, ParkingAccessLog> parkingAccess = new HashMap<>();

  @Override
  public List<ParkingAccessLog> findAll() {
    return Lists.newArrayList(parkingAccess.values());
  }

  @Override
  public void save(ParkingAccessLog parkingAccessLog) {

  }
}
