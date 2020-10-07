package ca.ulaval.glo4003.spamdul.infrastructure.calendar;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;

import java.time.LocalDateTime;

public class HardCodedCalendar implements Calendar {
    @Override
    public LocalDateTime getStartOfSemester(Semester semester) {
        switch (semester.getSeason()) {
            case 'A':
                return LocalDateTime.of(semester.getYear(), 9, 1, 0, 0);
            case 'H':
                return LocalDateTime.of(semester.getYear(), 1, 15, 0, 0);
            case 'E':
                return LocalDateTime.of(semester.getYear(), 5, 7, 0, 0);
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public LocalDateTime getEndOfSemester(Semester semester) {
        switch (semester.getSeason()) {
            case 'A':
                return LocalDateTime.of(semester.getYear(), 12, 23, 0, 0);
            case 'H':
                return LocalDateTime.of(semester.getYear(), 5, 5, 0, 0);
            case 'E':
                return LocalDateTime.of(semester.getYear(), 8, 15, 0, 0);
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
