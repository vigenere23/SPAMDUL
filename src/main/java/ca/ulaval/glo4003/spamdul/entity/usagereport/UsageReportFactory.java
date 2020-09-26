package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportFactory {
    private final ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;

    public UsageReportFactory(ParkingAccessLogAgglomerator parkingAccessLogAgglomerator) {
        this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    }

    public UsageReportSummary createSummaryReport(List<ParkingAccessLog> lastMonthAccesses) {
        LocalDate now = LocalDate.now();
        Map<LocalDate, List<ParkingAccessLog>> logsPerDay = parkingAccessLogAgglomerator.groupPerDay(lastMonthAccesses);

        float totalUsage = 0;
        int minNumberOfAccesses = Integer.MAX_VALUE;
        int maxNumberOfAccesses = 0;
        LocalDate mostPopularDayOfMonth = null;
        LocalDate leastPopularDayOfMonth = null;

        for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : logsPerDay.entrySet()) {
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
