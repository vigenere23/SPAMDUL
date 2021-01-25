package ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogIdFactory;
import org.junit.Test;

public class ParkingAccessLogQueryBuilderInMemoryTest {

  private ParkingAccessLogQueryBuilderInMemory parkingAccessLogQueryerInMemory;
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
  private final ParkingAccessLogIdFactory parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(new IncrementalIdGenerator());

  @Test
  public void givenDataSetAndNoFiltersAdded_whenGettingResults_shouldReturnDataSet() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogQueryerInMemory = new ParkingAccessLogQueryBuilderInMemory(logs);

    List<ParkingAccessLog> filteredLogs = parkingAccessLogQueryerInMemory.getAll();

    assertThat(filteredLogs).containsExactlyElementsIn(logs);
  }

  @Test
  public void givenResultsGet_whenGettingResultsASecondTime_shouldReturnSameResults() {
    List<ParkingAccessLog> logs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    parkingAccessLogQueryerInMemory = new ParkingAccessLogQueryBuilderInMemory(logs);
    List<ParkingAccessLog> filteredLogsFirstTime = parkingAccessLogQueryerInMemory.getAll();

    List<ParkingAccessLog> filteredLogsSecondTime = parkingAccessLogQueryerInMemory.getAll();

    assertThat(filteredLogsSecondTime).containsExactlyElementsIn(filteredLogsFirstTime);
  }

  @Test
  public void givenAccessLogTooEarly_whenFilteringBetweenDates_shouldReturnEmptyList() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(BEFORE_DATE);
    parkingAccessLogQueryerInMemory = new ParkingAccessLogQueryBuilderInMemory(Collections.singletonList(accessLogTooEarly));

    List<ParkingAccessLog> filteredLogs = parkingAccessLogQueryerInMemory.betweenDates(START_DATE, END_DATE).getAll();

    assertThat(filteredLogs).isEmpty();
  }

  @Test
  public void givenAccessLogTooLate_whenFilteringBetweenDates_shouldReturnEmptyList() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(AFTER_DATE);
    parkingAccessLogQueryerInMemory = new ParkingAccessLogQueryBuilderInMemory(Collections.singletonList(accessLogTooEarly));

    List<ParkingAccessLog> filteredLogs = parkingAccessLogQueryerInMemory.betweenDates(START_DATE, END_DATE).getAll();

    assertThat(filteredLogs).isEmpty();
  }

  @Test
  public void givenAccessLogBetweenDates_whenFilteringBetweenDates_shouldReturnTheGivenLog() {
    ParkingAccessLog accessLogTooEarly = createLogAtDate(BETWEEN_DATE);
    parkingAccessLogQueryerInMemory = new ParkingAccessLogQueryBuilderInMemory(Collections.singletonList(accessLogTooEarly));

    List<ParkingAccessLog> filteredLogs = parkingAccessLogQueryerInMemory.betweenDates(START_DATE, END_DATE).getAll();

    assertThat(filteredLogs).containsExactly(accessLogTooEarly);
  }

  private ParkingAccessLog createLogAtDate(LocalDate date) {
    return new ParkingAccessLog(parkingAccessLogIdFactory.create(), ParkingZone.ZONE_1, date);
  }
}
