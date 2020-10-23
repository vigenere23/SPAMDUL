package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Calendar {

  LocalDateTime getStartOfSemester(Semester semester);

  LocalDateTime getEndOfSemester(Semester semester);

  LocalDateTime now();

  LocalDateTime getStartOfSchoolYearAtDate(LocalDate date);

  LocalDateTime getEndOfSchoolYearAtDate(LocalDate date);
}
