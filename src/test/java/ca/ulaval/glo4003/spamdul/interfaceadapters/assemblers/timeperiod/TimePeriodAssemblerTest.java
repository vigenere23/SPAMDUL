package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.*;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterException;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TimePeriodAssemblerTest {
    private TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();

    @Test
    public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodDto_shouldHaveRightFields() {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
        timePeriodRequest.period = "single_day_per_week_per_semester";
        timePeriodRequest.dayOfWeek = "monday";
        timePeriodRequest.semester = "a2020";

        TimePeriodDto timePeriodDto = timePeriodAssembler.fromRequest(timePeriodRequest);

        assertThat(timePeriodDto.periodType).isEqualTo(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
        assertThat(timePeriodDto.dayOfWeek).isEqualTo(DayOfWeek.MONDAY);
        assertThat(timePeriodDto.semester).isEqualTo(new Semester('A', 2020));
    }

    @Test(expected = InvalidSemesterException.class)
    public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodWithInvalidSemesterSeason_shouldThrows() {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
        timePeriodRequest.period = "single_day_per_week_per_semester";
        timePeriodRequest.dayOfWeek = "monday";
        timePeriodRequest.semester = "g2020";

        timePeriodAssembler.fromRequest(timePeriodRequest);
    }

    @Test(expected = InvalidSemesterException.class)
    public void givenSingleDayPerWeekPeriod_whenAssemblingTimePeriodWithInvalidSemesterYear_shouldThrows() {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest();
        timePeriodRequest.period = "single_day_per_week_per_semester";
        timePeriodRequest.dayOfWeek = "monday";
        timePeriodRequest.semester = "Haghdfegj";

        timePeriodAssembler.fromRequest(timePeriodRequest);
    }
}
