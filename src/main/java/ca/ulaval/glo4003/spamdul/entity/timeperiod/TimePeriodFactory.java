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

    startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
    timePeriodDayOfWeek = timePeriodDto.timePeriodDayOfWeek;

    if (timePeriodDto.periodType == PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER
        || timePeriodDto.periodType == PeriodType.ONE_SEMESTER) {
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester);

    } else if (timePeriodDto.periodType == PeriodType.TWO_SEMESTERS) {
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester.addSemester(1));

    } else if (timePeriodDto.periodType == PeriodType.THREE_SEMESTERS) {
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester.addSemester(2));

    } else {
      throw new InvalidPeriodTypeException("The provided period is not valid");
    }

    return new TimePeriod(startDateTime, endDateTime, timePeriodDayOfWeek);
  }
}
