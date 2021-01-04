package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessDateException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessTimeException;
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
    validateDayOfWeek(accessDateTime);
    validateDate(accessDateTime);
    validateTime(accessDateTime);
  }

  private void validateDayOfWeek(LocalDateTime accessDateTime) {
    boolean isValid = days.stream().anyMatch(dayOfWeek -> dayOfWeek.equals(accessDateTime.getDayOfWeek()));

    if (!isValid) {
      throw new InvalidAccessDateException(accessDateTime.toLocalDate());
    }
  }

  private void validateDate(LocalDateTime accessDateTime) {
    LocalDate accessDate = accessDateTime.toLocalDate();
    boolean isValid = !accessDate.isBefore(periodStart) && !accessDate.isAfter(periodEnd);

    if (!isValid) {
      throw new InvalidAccessDateException(accessDate);
    }
  }

  private void validateTime(LocalDateTime accessDateTime) {
    LocalTime accessTime = accessDateTime.toLocalTime();
    boolean isValid = !accessTime.isBefore(startTime) && !accessTime.isAfter(endTime);

    if (!isValid) {
      throw new InvalidAccessTimeException(accessTime);
    }
  }
}
