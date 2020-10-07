package ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.DayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;

import java.time.LocalDate;

public class TimePeriodRequest {
    public PeriodType periodType;
    public LocalDate startDate;
    public LocalDate date;
    public String startSemester;
    public String semester;
    public DayOfWeek dayOfWeek;
}
