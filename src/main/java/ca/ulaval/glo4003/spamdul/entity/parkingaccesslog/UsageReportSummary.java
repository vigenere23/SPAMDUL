package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;
import java.util.Optional;

public class UsageReportSummary {
    private final float meanUsagePerDay;
    private final Optional<LocalDate> mostPopularDayOfMonth;
    private final Optional<LocalDate> leastPopularDayOfMonth;

    public UsageReportSummary(float meanUsagePerDay, LocalDate mostPopularDayOfMonth, LocalDate leastPopularDayOfMonth) {
        this.meanUsagePerDay = meanUsagePerDay;
        this.mostPopularDayOfMonth = Optional.ofNullable(mostPopularDayOfMonth);
        this.leastPopularDayOfMonth = Optional.ofNullable(leastPopularDayOfMonth);
    }

    public float getMeanUsagePerDay() {
        return meanUsagePerDay;
    }

    public Optional<LocalDate> getMostPopularDayOfMonth() {
        return mostPopularDayOfMonth;
    }

    public Optional<LocalDate> getLeastPopularDayOfMonth() {
        return leastPopularDayOfMonth;
    }
}
