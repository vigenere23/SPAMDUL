package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import static java.lang.Integer.parseInt;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Session;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterException;
import java.math.BigDecimal;
import java.util.Arrays;

public class TimePeriodAssembler {

  public TimePeriodDto fromRequest(TimePeriodRequest timePeriodRequest) {
    final String ERROR_MESSAGE = "make a choice between: " + Arrays.toString(PeriodType.values());
    TimePeriodDto timePeriodDto = new TimePeriodDto();

    try {
      timePeriodDto.periodType = PeriodType.valueOf(timePeriodRequest.type.toUpperCase());
    } catch (Exception e) {
      throw new InvalidPeriodArgumentException(ERROR_MESSAGE);
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
      throw new InvalidPeriodArgumentException("Number of hours must be between 1 and 23");
    }
    timePeriodDto.numberOfHours = BigDecimal.valueOf(timePeriodRequest.numberOfHours);
    timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
  }

  private void setFullSemestersSemesterDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    timePeriodDto.semester = assembleSemester(timePeriodRequest.semester);
    timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    //TODO Maybe throw an exception if there is a chosen day when choosing one two or three semster
  }

  private void setSingleDayPerWeekPerSemesterDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    timePeriodDto.semester = assembleSemester(timePeriodRequest.semester);
    try {
      timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.valueOf(timePeriodRequest.dayOfWeek.toUpperCase());
    } catch (Exception e) {
      throw new InvalidPeriodArgumentException("Day of the week must be from monday to friday");
    }
  }

  private Semester assembleSemester(String semester) {
    final String message = "The semester must be in format {A|H|E}XXXX";

    if (semester == null) {
      throw new InvalidSemesterException(message);
    }

    semester = semester.toUpperCase();

    int year;
    try {
      year = parseInt(semester.substring(1));
    } catch (Exception e) {
      throw new InvalidSemesterException(message);
    }

    Session session;
    try {
      session = Session.parse(semester.substring(0, 1));
    } catch (Exception e) {
      throw new InvalidSemesterException(message);
    }

    return new Semester(session, year);
  }
}
