package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportFactory {
    private final ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;

    public UsageReportFactory(ParkingAccessLogAgglomerator parkingAccessLogAgglomerator) {
        this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    }

    public UsageReportSummary create(List<ParkingAccessLog> lastMonthAccesses) {
        Map<LocalDate, List<ParkingAccessLog>> logsPerDay = parkingAccessLogAgglomerator.groupPerDay(lastMonthAccesses);

        float totalUsage = 0;
        int minNumberOfAccesses = Integer.MAX_VALUE;
        int maxNumberOfAccesses = 0;
        LocalDate dayWithMinUsage = null;
        LocalDate dayWithMaxUsage = null;

        for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : logsPerDay.entrySet()) {
            int numberOfAccesses = entry.getValue().size();
            totalUsage += numberOfAccesses;

            if (numberOfAccesses > maxNumberOfAccesses) {
                maxNumberOfAccesses = numberOfAccesses;
                dayWithMaxUsage = entry.getKey();
            }

            if (numberOfAccesses < minNumberOfAccesses) {
                minNumberOfAccesses = numberOfAccesses;
                dayWithMinUsage = entry.getKey();
            }
        }

        float meanUsage = totalUsage / logsPerDay.keySet().size();

        return new UsageReportSummary(meanUsage, dayWithMaxUsage, dayWithMinUsage);
    }
}
