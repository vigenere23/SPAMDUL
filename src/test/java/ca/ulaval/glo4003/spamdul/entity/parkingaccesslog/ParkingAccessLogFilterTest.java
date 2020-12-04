package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
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

  private final ParkingAccessLog AN_ACCESS_LOG = new ParkingAccessLog(ParkingAccessLogId.valueOf("123"),
                                                                      ParkingZone.ZONE_1,
                                                                      LocalDate.now());
  private final ParkingAccessLog AN_ACCESS_LOG_COPY = new ParkingAccessLog(ParkingAccessLogId.valueOf("456"),
                                                                           ParkingZone.ZONE_1,
                                                                           LocalDate.now());
  private final ParkingAccessLogIdFactory parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(new IncrementalLongIdGenerator());
  private ParkingAccessLogFactory parkingAccessLogFactory;

  @Before
  public void setUp() {
    parkingAccessLogFilter = new ParkingAccessLogFilter();
    parkingAccessLogFactory = new ParkingAccessLogFactory(parkingAccessLogIdFactory);
  }

  @Test
  public void givenDataSetAndNoFiltersAdded_whenGettingResults_shouldReturnDataSet() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogFilter.setData(logs);

    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

    assertThat(filteredLogs).containsExactlyElementsIn(logs);
  }

  @Test
  public void givenResultsGet_whenGettingResultsASecondTime_shouldReturnSameResults() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogFilter.setData(logs);
    List<ParkingAccessLog> filteredLogsFirstTime = parkingAccessLogFilter.getResults();

    List<ParkingAccessLog> filteredLogsSecondTime = parkingAccessLogFilter.getResults();

    assertThat(filteredLogsSecondTime).containsExactlyElementsIn(filteredLogsFirstTime);
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
    return parkingAccessLogFactory.create(ParkingZone.ZONE_1, date);
  }
}
