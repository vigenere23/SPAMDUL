package ca.ulaval.glo4003.spamdul.shared.entities.timeperiod;

import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidDateException;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidDayOfWeekException;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class TimePeriod {

  private final LocalDate periodStart;
  private final LocalDate periodEnd;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private Optional<DayOfWeek> dayOfWeek = Optional.empty();

  public TimePeriod(LocalDate periodStart,
                    LocalDate periodEnd,
                    LocalTime startTime,
                    LocalTime endTime,
                    DayOfWeek dayOfWeek) {
    this(periodStart, periodEnd, startTime, endTime);
    this.dayOfWeek = Optional.of(dayOfWeek);
  }

  public TimePeriod(LocalDate periodStart,
                    LocalDate periodEnd,
                    LocalTime startTime,
                    LocalTime endTime) {
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public void validateDateTime(LocalDateTime accessDateTime) {
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
      throw new InvalidDayOfWeekException(accessDateTime);
    }
  }

  private void validateDate(LocalDateTime accessDateTime) {
    LocalDate accessDate = accessDateTime.toLocalDate();
    boolean isValid = !accessDate.isBefore(periodStart) && !accessDate.isAfter(periodEnd);

    if (!isValid) {
      throw new InvalidDateException(accessDateTime);
    }
  }

  private void validateTime(LocalDateTime accessDateTime) {
    LocalTime accessTime = accessDateTime.toLocalTime();
    boolean isValid = !accessTime.isBefore(startTime) && !accessTime.isAfter(endTime);

    if (!isValid) {
      throw new InvalidTimeException(accessDateTime);
    }
  }

  public LocalDate getPeriodStart() {
    return periodStart;
  }

  public LocalDate getPeriodEnd() {
    return periodEnd;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public Optional<DayOfWeek> getDayOfWeek() {
    return dayOfWeek;
  }
}
