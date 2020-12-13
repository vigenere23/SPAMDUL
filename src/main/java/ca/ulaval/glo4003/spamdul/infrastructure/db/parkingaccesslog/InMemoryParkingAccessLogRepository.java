package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryParkingAccessLogRepository implements ParkingAccessLogRepository {

  private static final Map<ParkingAccessLogId, ParkingAccessLog> parkingAccessLogsById = new HashMap<>();

  @Override
  public List<ParkingAccessLog> findAll() {
    return Lists.newArrayList(parkingAccessLogsById.values());
  }

  @Override
  public void save(ParkingAccessLog parkingAccessLog) {
    parkingAccessLogsById.put(parkingAccessLog.getId(), parkingAccessLog);
  }

  public void clear() {
    parkingAccessLogsById.clear();
  }
}
