package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import java.time.LocalDate;
import java.util.Map;

public class UsageReport {

  private Map<LocalDate, Integer> usageReport;
  private ParkingZone parkingZones;

  public UsageReport(Map<LocalDate, Integer> usage, ParkingZone parkingZones) {
    this.usageReport = usage;
    this.parkingZones = parkingZones;
  }

  public Map<LocalDate, Integer> getUsageReport() {
    return usageReport;
  }

  public ParkingZone getParkingZones() {
    return parkingZones;
  }
}
