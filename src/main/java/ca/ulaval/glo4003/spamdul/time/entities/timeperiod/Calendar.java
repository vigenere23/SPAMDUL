package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Calendar {

  LocalDateTime getStartOfSemester(Semester semester);

  LocalDateTime getEndOfSemester(Semester semester);

  LocalDateTime now();

  LocalDateTime getStartOfSchoolYearAtDate(LocalDate date);

  LocalDateTime getEndOfSchoolYearAtDate(LocalDate date);

  DayOfWeek getDayOfWeek();
}
