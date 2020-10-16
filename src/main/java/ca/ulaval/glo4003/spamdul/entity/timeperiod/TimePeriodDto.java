package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDate;

public class TimePeriodDto {
    public PeriodType periodType;
    public LocalDate startDate;
    public LocalDate date;
    public Semester startSemester;
    public Semester semester;
    public TimePeriodDayOfWeek timePeriodDayOfWeek;
}