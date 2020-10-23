package ca.ulaval.glo4003.spamdul.infrastructure.calendar;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Season;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HardCodedCalendar implements Calendar {

  @Override
  public LocalDateTime getStartOfSemester(Semester semester) {
    switch (semester.getSeason()) {
      case A:
        return LocalDate.of(semester.getYear(), 9, 1).atStartOfDay();
      case H:
        return LocalDate.of(semester.getYear(), 1, 1).atStartOfDay();
      case E:
        return LocalDate.of(semester.getYear(), 5, 1).atStartOfDay();
      default:
        throw new RuntimeException("The given season is not valid");
    }
  }

  @Override
  public LocalDateTime getEndOfSemester(Semester semester) {
    switch (semester.getSeason()) {
      case A:
        return LocalDateTime.of(LocalDate.of(semester.getYear(), 12, 31), LocalTime.MAX);
      case H:
        return LocalDateTime.of(LocalDate.of(semester.getYear(), 4, 30), LocalTime.MAX);
      case E:
        return LocalDateTime.of(LocalDate.of(semester.getYear(), 8, 31), LocalTime.MAX);
      default:
        throw new RuntimeException("The given season is not valid");
    }
  }

  @Override
  public LocalDateTime now() {
    return LocalDateTime.now();
  }

  @Override
  public LocalDateTime getStartOfSchoolYearAtDate(LocalDate date) {
    LocalDateTime datetime = LocalDateTime.of(date, LocalTime.MIN);
    Season firstSeason = Season.A;

    int year = datetime.getYear();
    LocalDateTime startOfYear = getStartOfSemester(new Semester(firstSeason, year));

    if (startOfYear.isAfter(datetime)) {
      startOfYear = getStartOfSemester(new Semester(firstSeason, year - 1));
    }

    return startOfYear;
  }

  @Override
  public LocalDateTime getEndOfSchoolYearAtDate(LocalDate date) {
    LocalDateTime datetime = LocalDateTime.of(date, LocalTime.MAX);
    Season lastSeason = Season.E;

    int year = datetime.getYear();
    LocalDateTime endOfYear = getEndOfSemester(new Semester(lastSeason, year));

    if (endOfYear.isBefore(datetime)) {
      endOfYear = getEndOfSemester(new Semester(lastSeason, year + 1));
    }

    return endOfYear;
  }
}
