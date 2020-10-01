package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import java.util.Map;

public class UsageReport {

  private final Map<LocalDate, Integer> usageReport;
  private final ParkingZone parkingZone;
  private final Integer totalOfEntry;

  public UsageReport(Map<LocalDate, Integer> usageReport, ParkingZone parkingZone, Integer totalOfEntry) {
    this.usageReport = usageReport;
    this.parkingZone = parkingZone;
    this.totalOfEntry = totalOfEntry;
  }

  public Map<LocalDate, Integer> getUsageReport() {
    return usageReport;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public Integer getTotalOfEntry() {
    return totalOfEntry;
  }
}
