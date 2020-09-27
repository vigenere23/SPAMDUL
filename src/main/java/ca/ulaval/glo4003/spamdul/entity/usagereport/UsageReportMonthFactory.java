package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsageReportMonthFactory {
    public UsageReportMonth create(Map<LocalDate, List<ParkingAccessLog>> lastMonthAccessesPerDay) {
        Map<LocalDate, Integer> usageByDate = new HashMap<>();
        for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : lastMonthAccessesPerDay.entrySet()) {
            usageByDate.put(entry.getKey(), entry.getValue().size());
        }

        return new UsageReportMonth(usageByDate);
    }
}
