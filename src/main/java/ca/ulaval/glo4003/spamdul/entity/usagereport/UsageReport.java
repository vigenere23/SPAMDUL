package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import java.util.Map;

public class UsageReport {

  private Map<LocalDate, Integer> usageReport;
  private ParkingZone parkingZones;
  private Integer totalOfEntry;

  public UsageReport(Map<LocalDate, Integer> usageReport, ParkingZone parkingZones, Integer totalOfEntry) {
    this.usageReport = usageReport;
    this.parkingZones = parkingZones;
    this.totalOfEntry = totalOfEntry;
  }

  public Map<LocalDate, Integer> getUsageReport() {
    return usageReport;
  }

  public ParkingZone getParkingZones() {
    return parkingZones;
  }

  public Integer getTotalOfEntry() {
    return totalOfEntry;
  }
}
