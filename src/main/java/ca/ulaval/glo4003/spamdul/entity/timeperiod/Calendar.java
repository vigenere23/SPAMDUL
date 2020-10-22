package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public interface Calendar {

  LocalDateTime getStartOfSemester(Semester semester);

  LocalDateTime getEndOfSemester(Semester semester);

  LocalDateTime now();

  DayOfWeek getDayOfWeek();
}
