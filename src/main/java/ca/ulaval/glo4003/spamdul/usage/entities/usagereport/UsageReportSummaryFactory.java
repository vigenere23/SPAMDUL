package ca.ulaval.glo4003.spamdul.usage.entities.usagereport;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class UsageReportSummaryFactory {

  public UsageReportSummary create(Map<LocalDate, List<ParkingAccessLog>> monthAccessesPerDay,
                                   LocalDate startDate,
                                   LocalDate endDate) {
    return create(monthAccessesPerDay, startDate, endDate, null, null);
  }

  public UsageReportSummary create(Map<LocalDate, List<ParkingAccessLog>> monthAccessesPerDay,
                                   LocalDate startDate,
                                   LocalDate endDate,
                                   ParkingZone parkingZone,
                                   ParkingCategory parkingCategory) {
    float totalUsage = 0;
    int minNumberOfAccesses = Integer.MAX_VALUE;
    int maxNumberOfAccesses = 0;
    LocalDate mostPopularDayOfMonth = null;
    LocalDate leastPopularDayOfMonth = null;

    for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : monthAccessesPerDay.entrySet()) {
      int numberOfAccesses = entry.getValue().size();
      totalUsage += numberOfAccesses;

      if (numberOfAccesses > maxNumberOfAccesses) {
        maxNumberOfAccesses = numberOfAccesses;
        leastPopularDayOfMonth = entry.getKey();
      }

      if (numberOfAccesses < minNumberOfAccesses) {
        minNumberOfAccesses = numberOfAccesses;
        mostPopularDayOfMonth = entry.getKey();
      }
    }

    long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
    if (numberOfDays <= 0) {
      numberOfDays = 1;
    }
    float meanUsage = totalUsage / numberOfDays;

    return new UsageReportSummary(meanUsage,
                                  leastPopularDayOfMonth,
                                  mostPopularDayOfMonth,
                                  parkingZone,
                                  parkingCategory);
  }
}
