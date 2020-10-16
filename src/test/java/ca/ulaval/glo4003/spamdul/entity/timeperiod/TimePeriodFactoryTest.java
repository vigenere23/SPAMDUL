package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimePeriodFactoryTest {
    private static final Semester A_SEMESTER = new Semester('A', 2050);
    private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020,1,1,0,0);
    private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020,2,1,0,0);
    private static final TimePeriodDayOfWeek A_DAY_OF_WEEK = TimePeriodDayOfWeek.TUESDAY;

    @Mock
    private Calendar calendar;

    private TimePeriodFactory timePeriodFactory;

    @Before
    public void setUpFactory() {
        timePeriodFactory = new TimePeriodFactory(calendar);
    }

    @Test
    public void givenSingleDayPerWeekPeriodTypeInDto_whenCreatingPeriodType_shouldCallCalendarStartingDateOfSemester() {
        TimePeriodDto dto = new TimePeriodDto();
        dto.periodType = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
        dto.semester = A_SEMESTER;

        timePeriodFactory.createTimePeriod(dto);

        verify(calendar).getStartOfSemester(A_SEMESTER);
    }

    @Test
    public void givenSingleDayPerWeekPeriodTypeInDto_whenCreatingPeriodType_shouldCallCalendarEndDateOfSemester() {
        TimePeriodDto dto = new TimePeriodDto();
        dto.periodType = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
        dto.semester = A_SEMESTER;

        timePeriodFactory.createTimePeriod(dto);

        verify(calendar).getEndOfSemester(A_SEMESTER);
    }

    @Test
    public void givenSingleDayPerWeekPeriodTypeInDto_whenCreatingPeriodType_shouldCreateTimePeriodWithRightFields() {
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
}