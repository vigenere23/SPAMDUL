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

public class TimePeriodAssembler {

  public TimePeriodDto fromRequest(TimePeriodRequest timePeriodRequest) {
    final String ERROR_MESSAGE = "make a choice between (single_day, single_day_per_week_per_semester, one_semester, " +
        "two_semesters or three_semester) ";
    TimePeriodDto timePeriodDto = new TimePeriodDto();

    try {
      timePeriodDto.periodType = PeriodType.valueOf(timePeriodRequest.type.toUpperCase());
    } catch (Exception e) {
      throw new InvalidPeriodArgumentException(ERROR_MESSAGE);
    }

    if (timePeriodDto.periodType.equals(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER)) {
      setSingleDayPerWeekPerSemesterDto(timePeriodDto, timePeriodRequest);

    } else if (timePeriodDto.periodType.equals(PeriodType.ONE_SEMESTER)
        || timePeriodDto.periodType.equals(PeriodType.TWO_SEMESTERS)
        || timePeriodDto.periodType.equals(PeriodType.THREE_SEMESTERS)) {
      setFullSemestersSemesterDto(timePeriodDto, timePeriodRequest);

    } else {
      throw new UnsupportedOperationException("this feature will come later");
    }

    return timePeriodDto;
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
