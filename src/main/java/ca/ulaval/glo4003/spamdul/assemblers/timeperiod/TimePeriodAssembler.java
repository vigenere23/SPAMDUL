package ca.ulaval.glo4003.spamdul.assemblers.timeperiod;

import static java.lang.Integer.parseInt;

import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidDayOfWeekArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidNumberOfHoursArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidSemesterArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.time.api.timeperiod.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Session;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;
import java.math.BigDecimal;

public class TimePeriodAssembler {

  public TimePeriodDto fromRequest(TimePeriodRequest timePeriodRequest) {
    TimePeriodDto timePeriodDto = new TimePeriodDto();

    try {
      timePeriodDto.periodType = PeriodType.valueOf(timePeriodRequest.type.toUpperCase());
    } catch (Exception e) {
      throw new InvalidTimePeriodArgumentException();
    }

    switch (timePeriodDto.periodType) {
      case HOURLY:
        setHourlyDto(timePeriodDto, timePeriodRequest);
        break;
      case SINGLE_DAY_PER_WEEK_PER_SEMESTER:
        setSingleDayPerWeekPerSemesterDto(timePeriodDto, timePeriodRequest);
        break;
      case ONE_SEMESTER:
      case TWO_SEMESTERS:
      case THREE_SEMESTERS:
        setFullSemestersSemesterDto(timePeriodDto, timePeriodRequest);
        break;
      default:
        throw new UnsupportedOperationException("this feature will come later");
    }

    return timePeriodDto;
  }

  private void setHourlyDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    if (timePeriodRequest.numberOfHours < 1 || timePeriodRequest.numberOfHours > 23) {
      throw new InvalidNumberOfHoursArgumentException();
    }
    timePeriodDto.numberOfHours = BigDecimal.valueOf(timePeriodRequest.numberOfHours);
    timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
  }

  private void setFullSemestersSemesterDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    timePeriodDto.semester = assembleSemester(timePeriodRequest.semester);
    timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
  }

  private void setSingleDayPerWeekPerSemesterDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    timePeriodDto.semester = assembleSemester(timePeriodRequest.semester);
    try {
      timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.valueOf(timePeriodRequest.dayOfWeek.toUpperCase());
    } catch (Exception e) {
      throw new InvalidDayOfWeekArgumentException();
    }
  }

  private Semester assembleSemester(String semester) {
    if (semester == null) {
      throw new InvalidSemesterArgumentException();
    }

    semester = semester.toUpperCase();

    int year;
    try {
      year = parseInt(semester.substring(1));
    } catch (Exception e) {
      throw new InvalidSemesterArgumentException();
    }

    Session session;
    try {
      session = Session.parse(semester.substring(0, 1));
    } catch (Exception e) {
      throw new InvalidSemesterArgumentException();
    }

    return new Semester(session, year);
  }
}
