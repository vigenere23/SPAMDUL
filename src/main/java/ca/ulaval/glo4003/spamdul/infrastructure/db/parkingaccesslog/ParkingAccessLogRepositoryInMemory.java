package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jersey.repackaged.com.google.common.collect.Lists;

public class ParkingAccessLogRepositoryInMemory implements ParkingAccessLogRepository {

  private Map<ParkingAccessLogId, ParkingAccessLog> parkingAccessLogsById = new HashMap<>();
  private Map<LocalDate, ParkingAccessLog> parkingAccessLogsByDate = new HashMap<>();

  @Override
  public List<ParkingAccessLog> findAll() {
    return Lists.newArrayList(parkingAccessLogsById.values());
  }

  @Override
  public List<ParkingAccessLog> findAllWithFilter(ParkingAccessLogFilter filter) {
    return filter.setData(findAll()).getResults();
  }

  @Override
  public void save(ParkingAccessLog parkingAccessLog) {
    parkingAccessLogsById.put(parkingAccessLog.getId(), parkingAccessLog);
    parkingAccessLogsByDate.put(parkingAccessLog.getAccessDate(), parkingAccessLog);
  }
}
