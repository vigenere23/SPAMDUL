package ca.ulaval.glo4003.spamdul.entity.calendar;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;

import java.time.LocalDateTime;

public class TimePeriodFactory {
  private Calendar calendar;

  public TimePeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  TimePeriod getTimePeriod(TimePeriodDto timePeriodDto) {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    DayOfWeek dayOfWeek;

    if (timePeriodDto.period == Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
      endDateTime = calendar.getEndOfSemester(timePeriodDto.semester);
      dayOfWeek = timePeriodDto.dayOfWeek;
      return new TimePeriod(startDateTime, endDateTime, dayOfWeek);
    }

    return null;
  }

}
