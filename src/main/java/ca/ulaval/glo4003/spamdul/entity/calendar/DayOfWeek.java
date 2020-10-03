package ca.ulaval.glo4003.spamdul.entity.calendar;

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    ALL;

    public boolean includedIn(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.ALL || dayOfWeek == this;
    }
}
