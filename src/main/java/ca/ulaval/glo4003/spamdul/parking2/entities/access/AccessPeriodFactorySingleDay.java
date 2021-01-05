package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AccessPeriodFactorySingleDay extends AccessPeriodFactory {

  protected AccessPeriodFactorySingleDay(Calendar calendar) {
    super(calendar);
  }

  @Override
  public AccessPeriod create(LocalDateTime start, int multiplier, DayOfWeek dayOfWeek, Semester semester) {
    LocalDate date = start.toLocalDate();
    return new AccessPeriod(date, date, LocalTime.MIN, LocalTime.MAX);
  }
}
