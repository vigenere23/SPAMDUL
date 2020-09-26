package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.util.List;

public interface ParkingAccessLogRepository {
  List<ParkingAccessLog> findAll();
  List<ParkingAccessLog> findAllWithFilter(ParkingAccessLogFilter filter);

  void save(ParkingAccessLog parkingAccessLog);
}
