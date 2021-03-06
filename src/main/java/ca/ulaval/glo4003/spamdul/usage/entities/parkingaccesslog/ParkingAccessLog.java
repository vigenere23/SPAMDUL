package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;

public class ParkingAccessLog {

  private final ParkingAccessLogId id;
  private final ParkingZone zone;
  private final LocalDate accessDate;

  public ParkingAccessLog(ParkingAccessLogId id, ParkingZone zone, LocalDate accessDate) {
    this.id = id;
    this.zone = zone;
    this.accessDate = accessDate;
  }

  public ParkingZone getZone() {
    return zone;
  }

  public LocalDate getAccessDate() {
    return accessDate;
  }

  public ParkingAccessLogId getId() {
    return id;
  }
}
