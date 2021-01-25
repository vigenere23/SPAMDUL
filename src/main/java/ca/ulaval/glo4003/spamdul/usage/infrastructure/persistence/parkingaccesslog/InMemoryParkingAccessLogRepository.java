package ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.*;

import java.util.HashMap;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryParkingAccessLogRepository implements ParkingAccessLogRepository {

  private static final Map<ParkingAccessLogId, ParkingAccessLog> parkingAccessLogsById = new HashMap<>();

  @Override
  public ParkingAccessLogQueryBuilder find() {
    return new ParkingAccessLogQueryBuilderInMemory(Lists.newArrayList(parkingAccessLogsById.values()));
  }

  @Override
  public void save(ParkingAccessLog parkingAccessLog) {
    parkingAccessLogsById.put(parkingAccessLog.getId(), parkingAccessLog);
  }

  public void clear() {
    parkingAccessLogsById.clear();
  }
}
