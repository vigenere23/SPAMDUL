package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ParkingAccessLogAgglomeratorTest {

  private ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
  private final ParkingAccessLog AN_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                      ParkingZone.ZONE_1,
                                                                      LocalDate.now());
  private final ParkingAccessLog AN_ACCESS_LOG_COPY = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                           ParkingZone.ZONE_1,
                                                                           LocalDate.now());
  private final ParkingAccessLog AN_ACCESS_LOG_ONE_DAY_BEFORE = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                                     ParkingZone.ZONE_1,
                                                                                     LocalDate.now().minusDays(1));

  @Before
  public void setUp() {
    parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
  }

  @Test
  public void givenEmptyAccessLogList_whenGroupingByAccessDate_shouldReturnEmptyMap() {
    List<ParkingAccessLog> emptyAccessLogsList = new ArrayList<>();
    Map<LocalDate, List<ParkingAccessLog>> groupedLogs = parkingAccessLogAgglomerator.groupByAccessDate(
        emptyAccessLogsList);
    assertThat(groupedLogs).isEmpty();
  }

  @Test
  public void givenSingleAccessLog_whenGroupingByAccessDate_shouldReturnSingleEntryMap() {
    List<ParkingAccessLog> singleAccessLogList = Collections.singletonList(AN_ACCESS_LOG);
    Map<LocalDate, List<ParkingAccessLog>> groupedLogs = parkingAccessLogAgglomerator.groupByAccessDate(
        singleAccessLogList);
    assertThat(groupedLogs.entrySet()).hasSize(1);
    assertThat(groupedLogs.get(AN_ACCESS_LOG.getAccessDate())).containsExactly(AN_ACCESS_LOG);
  }

  @Test
  public void givenAccessLogsWithDifferentAccessDates_whenGroupingByAccessDate_shouldReturnTwoDifferentDateKeys() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_ONE_DAY_BEFORE);
    Map<LocalDate, List<ParkingAccessLog>> groupedLogs = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);
    assertThat(groupedLogs.keySet()).hasSize(2);
    assertThat(groupedLogs.get(AN_ACCESS_LOG.getAccessDate())).containsExactly(AN_ACCESS_LOG);
    assertThat(groupedLogs.get(AN_ACCESS_LOG_ONE_DAY_BEFORE.getAccessDate())).containsExactly(
        AN_ACCESS_LOG_ONE_DAY_BEFORE);
  }

  @Test
  public void givenAccessLogsWithSameAccessDate_whenGroupingByAccessDate_shouldReturnASingleKeyWithTwoElements() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    Map<LocalDate, List<ParkingAccessLog>> groupedLogs = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);
    assertThat(groupedLogs.keySet()).hasSize(1);
    assertThat(groupedLogs.get(AN_ACCESS_LOG.getAccessDate())).containsExactly(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
  }
}
