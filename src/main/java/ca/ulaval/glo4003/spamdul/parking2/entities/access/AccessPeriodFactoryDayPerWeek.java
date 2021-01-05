package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AccessPeriodFactoryDayPerWeek extends AccessPeriodFactory {

  protected AccessPeriodFactoryDayPerWeek(Calendar calendar) {
    super(calendar);
  }

  @Override
  public AccessPeriod create(LocalDateTime start, int multiplier, DayOfWeek dayOfWeek, Semester semester) {
    LocalDate semesterStart = calendar.getStartOfSemester(semester).toLocalDate();
    LocalDate semesterEnd = calendar.getEndOfSemester(semester).toLocalDate();

    return new AccessPeriod(semesterStart, semesterEnd, LocalTime.MIN, LocalTime.MAX, dayOfWeek);
  }
}
