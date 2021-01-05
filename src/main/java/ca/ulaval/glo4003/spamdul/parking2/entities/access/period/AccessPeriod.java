package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessDateException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AccessPeriod {

  private final LocalDate periodStart;
  private final LocalDate periodEnd;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private Optional<DayOfWeek> dayOfWeek = Optional.empty();

  public AccessPeriod(LocalDate periodStart,
                      LocalDate periodEnd,
                      LocalTime startTime,
                      LocalTime endTime,
                      DayOfWeek dayOfWeek) {
    this(periodStart, periodEnd, startTime, endTime);
    this.dayOfWeek = Optional.of(dayOfWeek);
  }

  public AccessPeriod(LocalDate periodStart,
                      LocalDate periodEnd,
                      LocalTime startTime,
                      LocalTime endTime) {
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    validateDayOfWeek(accessDateTime);
    validateDate(accessDateTime);
    validateTime(accessDateTime);
  }

  private void validateDayOfWeek(LocalDateTime accessDateTime) {
    if (!this.dayOfWeek.isPresent()) {
      return;
    }

    boolean isValid = dayOfWeek.get().equals(accessDateTime.getDayOfWeek());

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
