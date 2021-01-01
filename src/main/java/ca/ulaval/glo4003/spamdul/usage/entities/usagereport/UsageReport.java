package ca.ulaval.glo4003.spamdul.usage.entities.usagereport;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;
import java.util.Map;

public class UsageReport {

  private final Map<LocalDate, Integer> usageReport;
  private final ParkingZone parkingZone;
  private final ParkingCategory parkingCategory;
  private final Integer totalOfEntry;

  public UsageReport(Map<LocalDate, Integer> usageReport,
                     ParkingZone parkingZone,
                     ParkingCategory parkingCategory,
                     Integer totalOfEntry) {
    this.usageReport = usageReport;
    this.parkingZone = parkingZone;
    this.parkingCategory = parkingCategory;
    this.totalOfEntry = totalOfEntry;
  }

  public Map<LocalDate, Integer> getUsageReport() {
    return usageReport;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public ParkingCategory getParkingCategory() {
    return parkingCategory;
  }

  public Integer getTotalOfEntry() {
    return totalOfEntry;
  }
}
