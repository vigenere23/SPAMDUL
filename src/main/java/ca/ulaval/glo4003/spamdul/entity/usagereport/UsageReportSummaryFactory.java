package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportSummaryFactory {
    public UsageReportSummary create(Map<LocalDate, List<ParkingAccessLog>> lastMonthAccessesPerDay) {
        float totalUsage = 0;
        int minNumberOfAccesses = Integer.MAX_VALUE;
        int maxNumberOfAccesses = 0;
        LocalDate now = LocalDate.now();
        LocalDate mostPopularDayOfMonth = null;
        LocalDate leastPopularDayOfMonth = null;

        for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : lastMonthAccessesPerDay.entrySet()) {
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

        float meanUsage = totalUsage / now.getDayOfMonth();

        return new UsageReportSummary(meanUsage, leastPopularDayOfMonth, mostPopularDayOfMonth);
    }
}
