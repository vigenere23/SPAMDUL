package ca.ulaval.glo4003.spamdul.entity.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogIdFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class UsageReportSummaryFactoryTest {

  private UsageReportSummaryFactory usageReportSummaryFactory;
  private ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;

  private final LocalDate START_DATE = LocalDate.of(2011, 1, 1);
  private final LocalDate END_DATE = START_DATE.plusDays(27);
  private final long PERIOD_DAYS = ChronoUnit.DAYS.between(START_DATE, END_DATE) + 1;

  private final ParkingAccessLogIdFactory parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(new IncrementalLongIdGenerator());
  private final ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory(parkingAccessLogIdFactory);
  private final ParkingAccessLog AN_ACCESS_LOG = createLogForDate(START_DATE);
  private final ParkingAccessLog AN_ACCESS_LOG_COPY = createLogForDate(START_DATE);
  private final ParkingAccessLog AN_ACCESS_LOG_SOME_DAYS_AFTER = createLogForDate(END_DATE);

  @Before
  public void setUp() {
    usageReportSummaryFactory = new UsageReportSummaryFactory();
    parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
  }

  @Test
  public void givenEmptyAccessLogsMap_whenCreatingSummaryReport_shouldNotHaveMaxAndMinDates() {
    Map<LocalDate, List<ParkingAccessLog>> emptyLogsMap = new HashMap<>();

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(emptyLogsMap, START_DATE, END_DATE);

    assertThat(usageReportSummary.getLeastPopularDateOfMonth().isPresent()).isFalse();
    assertThat(usageReportSummary.getMostPopularDateOfMonth().isPresent()).isFalse();
  }

  @Test
  public void givenEmptyAccessLogsMap_whenCreatingSummaryReport_shouldHaveMeanUsageOfZero() {
    Map<LocalDate, List<ParkingAccessLog>> emptyLogsMap = new HashMap<>();
    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(emptyLogsMap, START_DATE, END_DATE);
    assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(0);
  }

  @Test
  public void givenAccessLogsForOneDay_whenCreatingSummaryReport_shouldHaveSameMaxAndMinDates() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay, START_DATE, END_DATE);
    LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
    LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

    assertThat(maxUsageDate).isEquivalentAccordingToCompareTo(minUsageDate);
  }

  @Test
  public void givenTwoAccessLogsForOneDay_whenCreatingSummaryReport_shouldHaveMeanUsageOfTwoOverPeriodDays() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
    Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay, START_DATE, END_DATE);

    assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(2.0f / PERIOD_DAYS);
  }

  @Test
  public void givenDifferentUsageEachDay_whenCreatingSummaryReport_shouldHaveDifferentMaxAndMinDates() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY, AN_ACCESS_LOG_SOME_DAYS_AFTER);
    Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay, START_DATE, END_DATE);
    LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
    LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

    assertThat(maxUsageDate).isNotEqualTo(minUsageDate);
  }

  @Test
  public void givenSingleUsagePerDay_whenCreatingSummaryReport_shouldHaveMeanUsageOfOne() {
    List<ParkingAccessLog> singleUsagePerDayLogs = generateOneAccessLogPerDay(START_DATE, END_DATE);
    Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        singleUsagePerDayLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay, START_DATE, END_DATE);

    assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(1);
  }

  @Test
  public void givenMultipleDifferentDays_whenCreatingSummaryReport_shouldHaveCorrectMinAndMaxDates() {
    List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY, AN_ACCESS_LOG_SOME_DAYS_AFTER);
    Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay, START_DATE, END_DATE);
    LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
    LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

    assertThat(maxUsageDate).isEqualTo(AN_ACCESS_LOG.getAccessDate());
    assertThat(minUsageDate).isEqualTo(AN_ACCESS_LOG_SOME_DAYS_AFTER.getAccessDate());
  }

  private List<ParkingAccessLog> generateOneAccessLogPerDay(LocalDate startDate, LocalDate endDate) {
    List<ParkingAccessLog> logs = new ArrayList<>();

    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      logs.add(createLogForDate(date));
    }

    return logs;
  }

  private ParkingAccessLog createLogForDate(LocalDate date) {
    return parkingAccessLogFactory.create(ParkingZone.ZONE_1, date);
  }
}
