package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObserver;
import java.util.List;

public interface ParkingAccessLogRepository extends AccessGrantedObserver {

  List<ParkingAccessLog> findAll();

  void save(ParkingAccessLog parkingAccessLog);
}
