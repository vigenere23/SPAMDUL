package ca.ulaval.glo4003.spamdul.infrastructure.calendar;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        throw new RuntimeException("bad season");
    }
  }

  @Override
  public LocalDateTime getEndOfSemester(Semester semester) {
    switch (semester.getSeason()) {
      case A:
        return LocalDateTime.of(semester.getYear(), 12, 31, 23, 59, 59);
      case H:
        return LocalDateTime.of(semester.getYear(), 4, 30, 23, 59, 59);
      case E:
        return LocalDateTime.of(semester.getYear(), 8, 31, 23, 59, 59);
      default:
        throw new RuntimeException("bad season");
    }
  }

  @Override
  public LocalDateTime now() {
    return LocalDateTime.now();
  }

  @Override
  public LocalDateTime getStartOfScholarYear() {
    return null;
  }

  @Override
  public LocalDateTime getEndOfScholarYear() {
    return null;
  }
}
