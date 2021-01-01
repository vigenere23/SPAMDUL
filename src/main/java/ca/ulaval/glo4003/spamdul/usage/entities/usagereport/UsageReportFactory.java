package ca.ulaval.glo4003.spamdul.usage.entities.usagereport;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsageReportFactory {

  public UsageReport create(Map<LocalDate, List<ParkingAccessLog>> accessesPerDay) {
    return create(accessesPerDay, null, null);
  }

  public UsageReport create(Map<LocalDate, List<ParkingAccessLog>> accessesPerDay,
                            ParkingZone parkingZone,
                            ParkingCategory parkingCategory) {
    Map<LocalDate, Integer> usageByDate = getLocalDateIntegerMap(accessesPerDay);
    Integer totalOfEntry = getTotalOfEntry(accessesPerDay);

    return new UsageReport(usageByDate, parkingZone, parkingCategory, totalOfEntry);
  }

  private Map<LocalDate, Integer> getLocalDateIntegerMap(Map<LocalDate, List<ParkingAccessLog>> accessesPerDay) {
    Map<LocalDate, Integer> usageByDate = new HashMap<>();

    for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : accessesPerDay.entrySet()) {
      usageByDate.put(entry.getKey(), entry.getValue().size());
    }

    return usageByDate;
  }

  private Integer getTotalOfEntry(Map<LocalDate, List<ParkingAccessLog>> accessesPerDay) {
    Integer totalOfEntry = 0;

    for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : accessesPerDay.entrySet()) {
      totalOfEntry += entry.getValue().size();
    }

    return totalOfEntry;
  }
}
