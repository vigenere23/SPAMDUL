package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimePeriod {

  private final LocalDateTime startDateTime;
  private final LocalDateTime endDateTime;
  private final TimePeriodDayOfWeek timePeriodDayOfWeek;

  public TimePeriod(LocalDateTime startDateTime,
                    LocalDateTime endDateTime,
                    TimePeriodDayOfWeek timePeriodDayOfWeek) {
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.timePeriodDayOfWeek = timePeriodDayOfWeek;
  }

  public boolean includedIn(TimePeriod that) {
    return !startDateTime.isBefore(that.startDateTime) &&
        !endDateTime.isAfter(that.endDateTime) &&
        timePeriodDayOfWeek.includedIn(that.timePeriodDayOfWeek);
  }

  public boolean includes(LocalDateTime localDateTime) {
    return !startDateTime.isAfter(localDateTime) &&
        !endDateTime.isBefore(localDateTime) &&
        timePeriodDayOfWeek.include(localDateTime.getDayOfWeek());
  }

  public boolean bounds(LocalDateTime localDateTime) {
    return !startDateTime.isAfter(localDateTime) &&
        !endDateTime.isBefore(localDateTime);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimePeriod that = (TimePeriod) o;

    return startDateTime.equals(that.startDateTime) &&
        endDateTime.equals(that.endDateTime) &&
        timePeriodDayOfWeek == that.timePeriodDayOfWeek;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDateTime, endDateTime, timePeriodDayOfWeek);
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public TimePeriodDayOfWeek getTimePeriodDayOfWeek() {
    return timePeriodDayOfWeek;
  }

  public boolean mayIncludeThisDayOfWeek(DayOfWeek dayOfWeek) {
    return timePeriodDayOfWeek.include(dayOfWeek);
  }
}
