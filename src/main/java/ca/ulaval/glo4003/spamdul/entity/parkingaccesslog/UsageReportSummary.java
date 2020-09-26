package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;
import java.util.Optional;

public class UsageReportSummary {
    private final float usagePerDayMean;
    private final Optional<LocalDate> mostPopularDayOfMonth;
    private final Optional<LocalDate> leastPopularDayOfMonth;

    public UsageReportSummary(float usagePerDayMean, LocalDate mostPopularDayOfMonth, LocalDate leastPopularDayOfMonth) {
        this.usagePerDayMean = usagePerDayMean;
        this.mostPopularDayOfMonth = Optional.ofNullable(mostPopularDayOfMonth);
        this.leastPopularDayOfMonth = Optional.ofNullable(leastPopularDayOfMonth);
    }

    public float getUsagePerDayMean() {
        return usagePerDayMean;
    }

    public Optional<LocalDate> getMostPopularDayOfMonth() {
        return mostPopularDayOfMonth;
    }

    public Optional<LocalDate> getLeastPopularDayOfMonth() {
        return leastPopularDayOfMonth;
    }
}
