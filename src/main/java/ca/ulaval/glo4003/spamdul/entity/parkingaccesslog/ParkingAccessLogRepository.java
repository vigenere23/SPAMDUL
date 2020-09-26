package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.util.List;

public interface ParkingAccessLogRepository {
  List<ParkingAccessLog> findAll();

  void save(ParkingAccessLog parkingAccessLog);
}
