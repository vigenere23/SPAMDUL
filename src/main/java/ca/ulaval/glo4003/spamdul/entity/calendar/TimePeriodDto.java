package ca.ulaval.glo4003.spamdul.entity.calendar;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;

import java.time.LocalDate;

public class TimePeriodDto {
    public Period period;
    public LocalDate startDate;
    public LocalDate date;
    public Semester startSemester;
    public Semester semester;
    public DayOfWeek dayOfWeek;
}
