package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;

public class UsageReportSummary {
    private float usagePerDayMean;
    private LocalDate mostPopularDayOfMonth;
    private LocalDate leastPopularDayOfMonth;

    public UsageReportSummary(float usagePerDayMean, LocalDate mostPopularDayOfMonth, LocalDate leastPopularDayOfMonth) {
        this.usagePerDayMean = usagePerDayMean;
        this.mostPopularDayOfMonth = mostPopularDayOfMonth;
        this.leastPopularDayOfMonth = leastPopularDayOfMonth;
    }

    public float getUsagePerDayMean() {
        return usagePerDayMean;
    }

    public LocalDate getMostPopularDayOfMonth() {
        return mostPopularDayOfMonth;
    }

    public LocalDate getLeastPopularDayOfMonth() {
        return leastPopularDayOfMonth;
    }
}
