package ca.ulaval.glo4003.spamdul.entity.timeperiod;

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    ALL;

    public boolean include(java.time.DayOfWeek javaTimeDayOfWeek) {
        return this == DayOfWeek.ALL ||
                this.toString().toUpperCase().equals(javaTimeDayOfWeek.toString().toUpperCase());
    }

    public boolean includedIn(DayOfWeek that) {
        return DayOfWeek.ALL == that || this == that;
    }
}
