package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class AccessPeriodCreationInfos {

  private final DayOfWeek dayOfWeek;
  private final int year;
  private final Month month;
  private final Semester semester;
  private final LocalDateTime start;
  private final int numberOfHours;

  public AccessPeriodCreationInfos(
      DayOfWeek dayOfWeek,
      int year,
      Month month,
      Semester semester,
      LocalDateTime start,
      int numberOfHours) {
    this.dayOfWeek = dayOfWeek;
    this.year = year;
    this.month = month;
    this.semester = semester;
    this.start = start;
    this.numberOfHours = numberOfHours;
  }

  public AccessPeriodCreationInfosHourly forHourly() {
    return new AccessPeriodCreationInfosHourly(start, numberOfHours);
  }

  public AccessPeriodCreationInfosSingleDay forSingleDay() {
    return new AccessPeriodCreationInfosSingleDay(start);
  }

  public AccessPeriodCreationInfosDayPerWeek forDayPerWeek() {
    return new AccessPeriodCreationInfosDayPerWeek(semester, dayOfWeek);
  }

  public AccessPeriodCreationInfosSession forSession() {
    return new AccessPeriodCreationInfosSession(semester);
  }

  public AccessPeriodCreationInfosMonth forMonth() {
    return new AccessPeriodCreationInfosMonth(year, month);
  }
}
