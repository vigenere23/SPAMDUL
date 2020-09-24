package ca.ulaval.glo4003.spamdul.infrastructure.ui.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.parkingaccesslog.dto.ParkingAccessLog;
import java.util.List;

public class ParkingAccessLogResourceImpl implements ParkingAccessLogResource {

  private ParkingAccessLogService parkingAccessLogService;

  public ParkingAccessLogResourceImpl(ParkingAccessLogService parkingAccessLogService) {
    this.parkingAccessLogService = parkingAccessLogService;
  }

  @Override
  public List<ParkingAccessLog> getParkingAccessLogLogs() {
    return null;
  }
}
