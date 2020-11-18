package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Session;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterException;
import java.math.BigDecimal;
import org.junit.Test;

public class TimePeriodAssemblerTest {

  private static final String A_VALID_SEMESTER = "a2020";
  private final TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();

  @Test
  public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodDto_shouldHaveRightFields() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "single_day_per_week_per_semester";
    timePeriodRequest.dayOfWeek = "monday";
    timePeriodRequest.semester = A_VALID_SEMESTER;

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    assertThat(timePeriodDto.periodType).isEqualTo(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    assertThat(timePeriodDto.timePeriodDayOfWeek).isEqualTo(TimePeriodDayOfWeek.MONDAY);
    assertThat(timePeriodDto.semester).isEqualTo(new Semester(Session.AUTUMN, 2020));
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodDtoWithInvalidDay_shouldThrow() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "single_day_per_week_per_semester";
    timePeriodRequest.dayOfWeek = "saturday";
    timePeriodRequest.semester = A_VALID_SEMESTER;

    timePeriodAssembler.fromRequest(timePeriodRequest);
  }

  @Test(expected = InvalidSemesterException.class)
  public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodWithInvalidSemesterSeason_shouldThrow() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "single_day_per_week_per_semester";
    timePeriodRequest.dayOfWeek = "monday";
    timePeriodRequest.semester = "g2020";

    timePeriodAssembler.fromRequest(timePeriodRequest);
  }

  @Test(expected = InvalidSemesterException.class)
  public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodWithInvalidSemesterYear_shouldThrow() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "single_day_per_week_per_semester";
    timePeriodRequest.dayOfWeek = "monday";
    timePeriodRequest.semester = "Haghdfegj";

    timePeriodAssembler.fromRequest(timePeriodRequest);
  }

  @Test
  public void givenOneSemester_whenAssemblingTimePeriodDto_shouldSetTheRightFields() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "one_semester";
    timePeriodRequest.semester = A_VALID_SEMESTER;

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    assertThat(timePeriodDto.periodType).isEqualTo(PeriodType.ONE_SEMESTER);
    assertThat(timePeriodDto.timePeriodDayOfWeek).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test
  public void givenTwoSemesters_whenAssemblingTimePeriodDto_shouldSetTheRightFields() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "two_semesters";
    timePeriodRequest.semester = A_VALID_SEMESTER;

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    assertThat(timePeriodDto.periodType).isEqualTo(PeriodType.TWO_SEMESTERS);
    assertThat(timePeriodDto.timePeriodDayOfWeek).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test
  public void givenThreeSemesters_whenAssemblingTimePeriodDto_shouldSetTheRightFields() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "three_semesters";
    timePeriodRequest.semester = A_VALID_SEMESTER;

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    assertThat(timePeriodDto.periodType).isEqualTo(PeriodType.THREE_SEMESTERS);
    assertThat(timePeriodDto.timePeriodDayOfWeek).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test
  public void givenHourlyPeriod_whenAssemblingTimePeriodDtoWithValidNumberOfHours_shouldSetTheRightFields() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "hourly";
    timePeriodRequest.numberOfHours = 15;
    timePeriodRequest.semester = A_VALID_SEMESTER;

    TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

    assertThat(timePeriodDto.numberOfHours).isEqualTo(BigDecimal.valueOf(15));
    assertThat(timePeriodDto.timePeriodDayOfWeek).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenHourlyPeriod_whenAssemblingTimePeriodDtoWithInvalidNumberOfHours_shouldThrow() {
    TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "hourly";
    timePeriodRequest.numberOfHours = 24;
    timePeriodRequest.semester = A_VALID_SEMESTER;
    timePeriodAssembler.fromRequest(timePeriodRequest);
  }
}
