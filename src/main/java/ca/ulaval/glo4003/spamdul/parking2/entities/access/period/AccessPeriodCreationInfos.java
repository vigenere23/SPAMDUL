package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class AccessPeriodCreationInfos {

  private final DayOfWeek dayOfWeek;
  private final int year;
  private final Month month;
  private final Semester semester;
  private final LocalTime startTime;
  private final LocalDate date;
  private final int numberOfHours;

  public AccessPeriodCreationInfos(
      DayOfWeek dayOfWeek,
      int year,
      Month month,
      Semester semester,
      LocalTime startTime,
      LocalDate date,
      int numberOfHours) {
    this.dayOfWeek = dayOfWeek;
    this.year = year;
    this.month = month;
    this.semester = semester;
    this.startTime = startTime;
    this.date = date;
    this.numberOfHours = numberOfHours;
  }

  public AccessPeriodCreationInfosHourly forHourly() {
    return new AccessPeriodCreationInfosHourly(date, startTime, numberOfHours);
  }

  public AccessPeriodCreationInfosSingleDay forSingleDay() {
    return new AccessPeriodCreationInfosSingleDay(date);
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
