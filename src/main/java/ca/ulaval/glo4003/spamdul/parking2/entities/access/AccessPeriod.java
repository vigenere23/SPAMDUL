package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AccessPeriod {

  private final LocalDate periodStart;
  private final LocalDate periodEnd;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final List<DayOfWeek> days;

  public AccessPeriod(LocalDate periodStart,
                      LocalDate periodEnd,
                      LocalTime startTime,
                      LocalTime endTime, List<DayOfWeek> days) {
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.startTime = startTime;
    this.endTime = endTime;
    this.days = days;
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    if (!(validateDayOfWeek(accessDateTime) && validateDate(accessDateTime) && validateTime(accessDateTime))) {
      throw new InvalidAccess();
    }
  }

  private boolean validateDayOfWeek(LocalDateTime accessDateTime) {
    return days.stream().anyMatch(dayOfWeek -> dayOfWeek.equals(accessDateTime.getDayOfWeek()));
  }

  private boolean validateDate(LocalDateTime accessDateTime) {
    LocalDate accessDate = accessDateTime.toLocalDate();
    return !accessDate.isBefore(periodStart) && !accessDate.isAfter(periodEnd);
  }

  private boolean validateTime(LocalDateTime accessDateTime) {
    LocalTime accessTime = accessDateTime.toLocalTime();
    return !accessTime.isBefore(startTime) && !accessTime.isAfter(endTime);
  }
}
