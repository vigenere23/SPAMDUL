package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParkingAccessLogFilterTest {

  private ParkingAccessLogFilter parkingAccessLogFilter;
  private final LocalDate START_DATE = LocalDate.of(2010, 1, 1);
  private final LocalDate END_DATE = START_DATE.plusDays(28);
  private final LocalDate BETWEEN_DATE = START_DATE.plusDays(15);
  private final LocalDate BEFORE_DATE = START_DATE.minusDays(1);
  private final LocalDate AFTER_DATE = END_DATE.plusDays(1);

  private final ParkingAccessLog AN_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                      ParkingZone.ZONE_1,
                                                                      LocalDate.now());
  private final ParkingAccessLog AN_ACCESS_LOG_COPY = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                           ParkingZone.ZONE_1,
                                                                           LocalDate.now());

  @Before
  public void setUp() {
    parkingAccessLogFilter = new ParkingAccessLogFilter();
  }

  @Test
  public void givenDataSet_whenGettingResults_shouldReturnDataSet() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogFilter.setData(logs);

    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

    assertThat(filteredLogs).containsExactlyElementsIn(logs);
  }

  @Test
  public void givenResultsGet_whenGettingResultsASecondTime_shouldReturnEmptyList() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogFilter.setData(logs);
    parkingAccessLogFilter.getResults();

    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

    assertThat(filteredLogs).isEmpty();
  }

  @Test
  public void givenAccessLogTooEarly_whenFilteringBetweenDates_shouldReturnEmptyList() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(BEFORE_DATE);
    parkingAccessLogFilter.setData(Collections.singletonList(accessLogTooEarly));
    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredLogs).isEmpty();
  }

  @Test
  public void givenAccessLogTooLate_whenFilteringBetweenDates_shouldReturnEmptyList() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(AFTER_DATE);
    parkingAccessLogFilter.setData(Collections.singletonList(accessLogTooEarly));
    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredLogs).isEmpty();
  }

  @Test
  public void givenAccessLogBetweenDates_whenFilteringBetweenDates_shouldReturnTheGivenLog() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(BETWEEN_DATE);
    parkingAccessLogFilter.setData(Collections.singletonList(accessLogTooEarly));
    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredLogs).containsExactly(accessLogTooEarly);
  }

  private ParkingAccessLog createLogAtDate(LocalDate date) {
    return new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, date);
  }
}
