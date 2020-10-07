package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimePeriod {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private DayOfWeek dayOfWeek;

    public TimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime, DayOfWeek dayOfWeek) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean includedIn(TimePeriod that) {
        return !startDateTime.isBefore(that.startDateTime) &&
                !endDateTime.isAfter(that.endDateTime) &&
                dayOfWeek.includedIn(that.dayOfWeek);
    }

    public boolean include(LocalDateTime localDateTime) {
        return !startDateTime.isAfter(localDateTime) &&
                !endDateTime.isBefore(localDateTime) &&
                dayOfWeek.include(localDateTime.getDayOfWeek());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod that = (TimePeriod) o;
        return startDateTime.equals(that.startDateTime) &&
                endDateTime.equals(that.endDateTime) &&
                dayOfWeek == that.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, dayOfWeek);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
