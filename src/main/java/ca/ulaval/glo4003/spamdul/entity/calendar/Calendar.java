package ca.ulaval.glo4003.spamdul.entity.calendar;

import java.time.LocalDateTime;

public interface Calendar {

    LocalDateTime getStartOfSemester(Semester semester);

    LocalDateTime getEndOfSemester(Semester semester);
}
