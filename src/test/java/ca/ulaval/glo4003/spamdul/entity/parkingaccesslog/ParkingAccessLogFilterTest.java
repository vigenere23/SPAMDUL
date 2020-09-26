package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ParkingAccessLogFilterTest {

    private ParkingAccessLogFilter parkingAccessLogFilter;
    private final ParkingAccessLog TODAY_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());
    private final ParkingAccessLog TODAY_ACCESS_LOG_COPY = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());
    private final ParkingAccessLog FUTURE_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now().plusDays(1));
    private final ParkingAccessLog PREVIOUS_MONTH_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now().minusMonths(1));

    @Before
    public void setUp() {
        parkingAccessLogFilter = new ParkingAccessLogFilter();
    }

    @Test
    public void givenDataSet_whenGettingResults_shouldReturnDataSet() {
        List<ParkingAccessLog> logs = Arrays.asList(TODAY_ACCESS_LOG, TODAY_ACCESS_LOG_COPY);
        parkingAccessLogFilter.setData(logs);

        List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

        assertThat(filteredLogs).containsExactlyElementsIn(logs);
    }

    @Test
    public void givenResultsGet_whenGettingResultsASecondTime_shouldReturnEmptyList() {
        List<ParkingAccessLog> logs = Arrays.asList(TODAY_ACCESS_LOG, TODAY_ACCESS_LOG_COPY);
        parkingAccessLogFilter.setData(logs);
        parkingAccessLogFilter.getResults();

        List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenFutureAccessLogDate_whenFilteringFromOngoingMonth_shouldReturnEmptyList() {
        parkingAccessLogFilter.setData(Collections.singletonList(FUTURE_ACCESS_LOG));
        List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.fromOngoingMonth().getResults();
        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenAccessLogDateFromPreviousMonth_whenFilteringFromOngoingMonth_shouldReturnEmptyList() {
        parkingAccessLogFilter.setData(Collections.singletonList(PREVIOUS_MONTH_ACCESS_LOG));
        List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.fromOngoingMonth().getResults();
        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenTodayAccessLogDate_whenFilteringFromOngoingMonth_shouldReturnThatAccessLog() {
        parkingAccessLogFilter.setData(Collections.singletonList(TODAY_ACCESS_LOG));
        List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.fromOngoingMonth().getResults();
        assertThat(filteredLogs).containsExactly(TODAY_ACCESS_LOG);
    }
}
