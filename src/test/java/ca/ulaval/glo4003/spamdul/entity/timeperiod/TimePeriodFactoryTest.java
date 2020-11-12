package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimePeriodFactoryTest {

  private static final Semester A_SEMESTER = new Semester(Session.AUTUMN, 2050);
  private static final Semester NEXT_SEMESTER = A_SEMESTER.addSemester(1);
  private static final Semester NEXT_NEXT_SEMESTER = NEXT_SEMESTER.addSemester(1);
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final TimePeriodDayOfWeek A_DAY_OF_WEEK = TimePeriodDayOfWeek.TUESDAY;
  public static final PeriodType A_PERIOD_TYPE = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;

  @Mock
  private Calendar calendar;

  private TimePeriodFactory timePeriodFactory;

  @Before
  public void setUpFactory() {
    timePeriodFactory = new TimePeriodFactory(calendar);
  }

  @Test
  public void whenCreatingTimePeriod_shouldCallCalendarStartingDateOfSemester() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = A_PERIOD_TYPE;
    dto.semester = A_SEMESTER;

    timePeriodFactory.createTimePeriod(dto);

    verify(calendar).getStartOfSemester(A_SEMESTER);
  }

  @Test
  public void givenSingleDayPerWeekPeriodTypeInDto_whenCreatingTimePeriod_shouldCreateTimePeriodWithRightFields() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
    dto.semester = A_SEMESTER;
    dto.timePeriodDayOfWeek = A_DAY_OF_WEEK;
    when(calendar.getStartOfSemester(A_SEMESTER)).thenReturn(A_START_DATE_TIME);
    when(calendar.getEndOfSemester(A_SEMESTER)).thenReturn(A_END_DATE_TIME);

    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(dto);

    assertThat(timePeriod.getStartDateTime()).isEqualTo(A_START_DATE_TIME);
    assertThat(timePeriod.getEndDateTime()).isEqualTo(A_END_DATE_TIME);
    assertThat(timePeriod.getTimePeriodDayOfWeek()).isEqualTo(A_DAY_OF_WEEK);
  }

  @Test
  public void givenOneSemesterPeriodTypeInDto_whenCreatingTimePeriod_shouldCreateTimePeriodWithRightFields() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = PeriodType.ONE_SEMESTER;
    dto.semester = A_SEMESTER;
    dto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(calendar.getStartOfSemester(A_SEMESTER)).thenReturn(A_START_DATE_TIME);
    when(calendar.getEndOfSemester(A_SEMESTER)).thenReturn(A_END_DATE_TIME);

    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(dto);

    assertThat(timePeriod.getStartDateTime()).isEqualTo(A_START_DATE_TIME);
    assertThat(timePeriod.getEndDateTime()).isEqualTo(A_END_DATE_TIME);
    assertThat(timePeriod.getTimePeriodDayOfWeek()).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test
  public void givenTwoSemesterPeriodTypeInDto_whenCreatingTimePeriod_shouldCreateTimePeriodWithRightFields() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = PeriodType.TWO_SEMESTERS;
    dto.semester = A_SEMESTER;
    dto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(calendar.getStartOfSemester(A_SEMESTER)).thenReturn(A_START_DATE_TIME);
    when(calendar.getEndOfSemester(NEXT_SEMESTER)).thenReturn(A_END_DATE_TIME);

    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(dto);

    assertThat(timePeriod.getStartDateTime()).isEqualTo(A_START_DATE_TIME);
    assertThat(timePeriod.getEndDateTime()).isEqualTo(A_END_DATE_TIME);
    assertThat(timePeriod.getTimePeriodDayOfWeek()).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test
  public void givenThreeSemesterPeriodTypeInDto_whenCreatingTimePeriod_shouldCreateTimePeriodWithRightFields() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = PeriodType.THREE_SEMESTERS;
    dto.semester = A_SEMESTER;
    dto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(calendar.getStartOfSemester(A_SEMESTER)).thenReturn(A_START_DATE_TIME);
    when(calendar.getEndOfSemester(NEXT_NEXT_SEMESTER)).thenReturn(A_END_DATE_TIME);

    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(dto);

    assertThat(timePeriod.getStartDateTime()).isEqualTo(A_START_DATE_TIME);
    assertThat(timePeriod.getEndDateTime()).isEqualTo(A_END_DATE_TIME);
    assertThat(timePeriod.getTimePeriodDayOfWeek()).isEqualTo(TimePeriodDayOfWeek.ALL);
  }

  @Test(expected = InvalidPeriodTypeException.class)
  public void givenAnInvalidPeriodType_whenCreatingPeriodTimePeriod_shouldThrowInvalidPeriodTypeException() {
    TimePeriodDto dto = new TimePeriodDto();
    dto.periodType = null;
    dto.semester = A_SEMESTER;
    dto.timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(calendar.getStartOfSemester(A_SEMESTER)).thenReturn(A_START_DATE_TIME);
    when(calendar.getEndOfSemester(NEXT_NEXT_SEMESTER)).thenReturn(A_END_DATE_TIME);

    timePeriodFactory.createTimePeriod(dto);
  }
}
