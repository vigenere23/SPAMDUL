package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import java.time.DayOfWeek;

public enum TimePeriodDayOfWeek {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  ALL;

  public boolean include(DayOfWeek javaTimeDayOfWeek) {
    return this == TimePeriodDayOfWeek.ALL ||
        this.toString().toUpperCase().equals(javaTimeDayOfWeek.toString().toUpperCase());
  }

  public boolean includedIn(TimePeriodDayOfWeek that) {
    return TimePeriodDayOfWeek.ALL == that || this == that;
  }
}
