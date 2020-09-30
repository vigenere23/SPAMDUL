package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;

public class ParkingAccessLogFactory {

  public ParkingAccessLog create(ParkingZone zone, LocalDate accessDate) {
    return new ParkingAccessLog(new ParkingAccessLogId(), zone, accessDate);
  }
}