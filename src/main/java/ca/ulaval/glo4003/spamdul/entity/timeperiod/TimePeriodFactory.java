package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDateTime;

public class TimePeriodFactory {

  private final Calendar calendar;

  public TimePeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  public TimePeriod createTimePeriod(TimePeriodDto timePeriodDto) {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    TimePeriodDayOfWeek timePeriodDayOfWeek;

    if (timePeriodDto.periodType == PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER
        || timePeriodDto.periodType == PeriodType.ONE_SEMESTER
        || timePeriodDto.periodType == PeriodType.TWO_SEMESTERS
        || timePeriodDto.periodType == PeriodType.THREE_SEMESTERS) {

      startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester);
      timePeriodDayOfWeek = timePeriodDto.timePeriodDayOfWeek;

      return new TimePeriod(startDateTime, endDateTime, timePeriodDayOfWeek);
    }

    return null;
  }
}
