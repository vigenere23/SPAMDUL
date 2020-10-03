package ca.ulaval.glo4003.spamdul.entity.calendar;

import java.time.LocalDateTime;

public class TimePeriod {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private DayOfWeek dayOfWeek;

    public TimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime, DayOfWeek dayOfWeek) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean includedIn(TimePeriod timePeriod) {
        return !startDateTime.isBefore(timePeriod.startDateTime) &&
                !endDateTime.isAfter(timePeriod.endDateTime) &&
                dayOfWeek.includedIn(timePeriod.dayOfWeek);
    }
}
