package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAssembler;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import java.util.List;

public class ParkingAccessLogService {
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private ParkingAccessLogAssembler parkingAccessLogAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogAssembler parkingAccessLogAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogAssembler = parkingAccessLogAssembler;
  }

  public List<ParkingAccessLog> findAllParkingAccess() {
    return null;
  }
}
