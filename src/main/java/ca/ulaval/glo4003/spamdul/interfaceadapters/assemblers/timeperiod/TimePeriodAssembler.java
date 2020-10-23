package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import static java.lang.Integer.parseInt;
import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterException;
import java.util.ArrayList;

public class TimePeriodAssembler {

  private final ArrayList<Character> VALID_SEASONS = newArrayList('H', 'E', 'A');

  public TimePeriodDto fromRequest(TimePeriodRequest timePeriodRequest) {
    TimePeriodDto timePeriodDto = new TimePeriodDto();
    timePeriodDto.periodType = PeriodType.valueOf(timePeriodRequest.type.toUpperCase());

    if (timePeriodDto.periodType.equals(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER)) {
      setSingleDayPerWeekPerSemesterDto(timePeriodDto, timePeriodRequest);
    } else {
      throw new UnsupportedOperationException("this feature will come later");
    }

    return timePeriodDto;
  }

  private void setSingleDayPerWeekPerSemesterDto(TimePeriodDto timePeriodDto, TimePeriodRequest timePeriodRequest) {
    timePeriodDto.semester = assembleSemester(timePeriodRequest.semester);
    try {
      timePeriodDto.timePeriodDayOfWeek = TimePeriodDayOfWeek.valueOf(timePeriodRequest.dayOfWeek.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidPeriodArgumentException("Day of the week must be from monday to friday");
    }
  }

  private Semester assembleSemester(String semester) {
    final String message = "The semester must be in format {A|H|E}XXXX";
    semester = semester.toUpperCase();
    char season = semester.charAt(0);
    int year;
    try {
      year = parseInt(semester.substring(1));
    } catch (Exception e) {
      throw new InvalidSemesterException(message);
    }
    if (!VALID_SEASONS.contains(season)) {
      throw new InvalidSemesterException(message);
    }
    return new Semester(season, year);
  }
}
