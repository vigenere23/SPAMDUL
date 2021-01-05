package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AccessPeriodFactoryHourly extends AccessPeriodFactory {

  protected AccessPeriodFactoryHourly(Calendar calendar) {
    super(calendar);
  }

  @Override
  public AccessPeriod create(LocalDateTime start, int multiplier, DayOfWeek dayOfWeek, Semester semester) {
    LocalDateTime end = start.plusHours(multiplier);
    LocalDate periodStart = start.toLocalDate();
    LocalDate periodEnd = end.toLocalDate();
    LocalTime startTime = start.toLocalTime();
    LocalTime endTime = end.toLocalTime();

    return new AccessPeriod(periodStart, periodEnd, startTime, endTime);
  }
}
