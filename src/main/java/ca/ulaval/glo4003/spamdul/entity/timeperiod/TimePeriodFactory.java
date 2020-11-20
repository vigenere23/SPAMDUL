package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.time.LocalDateTime;

public class TimePeriodFactory {

  private final Calendar calendar;

  public TimePeriodFactory(Calendar calendar) {
    this.calendar = calendar;
  }

  public TimePeriod createTimePeriod(TimePeriodDto timePeriodDto) {
    if (timePeriodDto.periodType == null) {
      throw new InvalidPeriodTypeException();
    }

    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    switch (timePeriodDto.periodType) {
      case HOURLY:
        startDateTime = LocalDateTime.now();
        endDateTime = LocalDateTime.MAX;
        break;
      case SINGLE_DAY_PER_WEEK_PER_SEMESTER:
      case ONE_SEMESTER:
        startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
        endDateTime = calendar.getEndOfSemester(timePeriodDto.semester);
        break;
      case TWO_SEMESTERS:
        startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
        endDateTime = calendar.getEndOfSemester(timePeriodDto.semester.addSemester(1));
        break;
      case THREE_SEMESTERS:
        startDateTime = calendar.getStartOfSemester(timePeriodDto.semester);
        endDateTime = calendar.getEndOfSemester(timePeriodDto.semester.addSemester(2));
        break;
      default:
        throw new InvalidPeriodTypeException();
    }

    return new TimePeriod(startDateTime,
                          endDateTime,
                          timePeriodDto.timePeriodDayOfWeek,
                          timePeriodDto.numberOfHours);
  }
}
