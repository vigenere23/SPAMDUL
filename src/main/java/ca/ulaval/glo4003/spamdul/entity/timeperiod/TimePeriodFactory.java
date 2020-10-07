package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDateTime;

public class TimePeriodFactory {
  private Calendar calendar;

  public TimePeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  public TimePeriod createTimePeriod(TimePeriodDto timePeriodDto) {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    DayOfWeek dayOfWeek;

    if (timePeriodDto.periodType == PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester);
      dayOfWeek = timePeriodDto.dayOfWeek;
      return new TimePeriod(startDateTime, endDateTime, dayOfWeek);
    }

    return null;
  }

}
