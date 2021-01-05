package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class AccessPeriodFactoryOld {

  private final Calendar calendar;

  public AccessPeriodFactoryOld(Calendar calendar) {
    this.calendar = calendar;
  }

  public AccessPeriod createSemesters(Semester firstSemester, int numberOfSemesters) {
    if (numberOfSemesters < 1 || numberOfSemesters > 3) {
      throw new RuntimeException("number of semesters must be between 1 and 3");
    }

    LocalDate firstSemesterStart = calendar.getStartOfSemester(firstSemester).toLocalDate();
    LocalDate lastSemesterEnd = calendar.getEndOfSemester(firstSemester.plusSemesters(numberOfSemesters)).toLocalDate();

    return new AccessPeriod(firstSemesterStart,
                            lastSemesterEnd,
                            LocalTime.MIN,
                            LocalTime.MAX);
  }

  public AccessPeriod createMonth(int year, Month month) {
    LocalDate periodStart = LocalDate.of(year, month, 1);
    LocalDate periodEnd = periodStart.withDayOfMonth(periodStart.lengthOfMonth());

    return new AccessPeriod(periodStart, periodEnd, LocalTime.MIN, LocalTime.MAX);
  }

  public AccessPeriod createOneDayPerWeekForOneSemester(Semester semester, DayOfWeek dayOfWeek) {
    LocalDate semesterStart = calendar.getStartOfSemester(semester).toLocalDate();
    LocalDate semesterEnd = calendar.getEndOfSemester(semester).toLocalDate();

    return new AccessPeriod(semesterStart, semesterEnd, LocalTime.MIN, LocalTime.MAX, dayOfWeek);
  }
}
