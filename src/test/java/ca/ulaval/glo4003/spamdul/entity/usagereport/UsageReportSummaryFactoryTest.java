package ca.ulaval.glo4003.spamdul.entity.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class UsageReportSummaryFactoryTest {

    private UsageReportSummaryFactory usageReportSummaryFactory;
    private ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
    private final ParkingAccessLog AN_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());
    private final ParkingAccessLog AN_ACCESS_LOG_COPY = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());
    private final ParkingAccessLog AN_ACCESS_LOG_ONE_DAY_BEFORE = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now().minusDays(1));

    @Before
    public void setUp() {
        usageReportSummaryFactory = new UsageReportSummaryFactory();
        parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
    }

    @Test
    public void givenEmptyAccessLogsMap_whenCreatingSummaryReport_shouldNotHaveMaxAndMinDates() {
        Map<LocalDate, List<ParkingAccessLog>> emptyLogsMap = new HashMap<>();

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(emptyLogsMap);

        assertThat(usageReportSummary.getLeastPopularDateOfMonth().isPresent()).isFalse();
        assertThat(usageReportSummary.getMostPopularDateOfMonth().isPresent()).isFalse();
    }

    @Test
    public void givenEmptyAccessLogsMap_whenCreatingSummaryReport_shouldHaveMeanUsageOfZero() {
        Map<LocalDate, List<ParkingAccessLog>> emptyLogsMap = new HashMap<>();
        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(emptyLogsMap);
        assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(0);
    }

    @Test
    public void givenAccessLogsForOneDay_whenCreatingSummaryReport_shouldHaveSameMaxAndMinDates() {
        List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
        Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay);
        LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
        LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

        assertThat(maxUsageDate).isEquivalentAccordingToCompareTo(minUsageDate);
    }

    @Test
    public void givenTwoAccessLogsForOneDay_whenCreatingSummaryReport_shouldHaveMeanUsageOfTwoOverMonthDay() {
        List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY);
        Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay);
        float monthDay = AN_ACCESS_LOG.getAccessDate().getDayOfMonth();

        assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(2 / monthDay);
    }

    @Test
    public void givenDifferentUsageEachDay_whenCreatingSummaryReport_shouldHaveDifferentMaxAndMinDates() {
        List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY, AN_ACCESS_LOG_ONE_DAY_BEFORE);
        Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay);
        LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
        LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

        assertThat(maxUsageDate).isGreaterThan(minUsageDate);
    }

    @Test
    public void givenSingleUsagePerDay_whenCreatingSummaryReport_shouldHaveMeanUsageOfOne() {
        List<ParkingAccessLog> singleUsagePerDayLogs = generateOneAccessLogPerDay(LocalDate.now().getDayOfMonth());
        Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(singleUsagePerDayLogs);

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay);

        assertThat(usageReportSummary.getMeanUsagePerDay()).isEqualTo(1);
    }

    @Test
    public void givenMultipleDifferentDays_whenCreatingSummaryReport_shouldHaveCorrectMinAndMaxDates() {
        List<ParkingAccessLog> accessLogs = Arrays.asList(AN_ACCESS_LOG, AN_ACCESS_LOG_COPY, AN_ACCESS_LOG_ONE_DAY_BEFORE);
        Map<LocalDate, List<ParkingAccessLog>> accessLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(accessLogs);

        UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(accessLogsPerDay);
        LocalDate maxUsageDate = usageReportSummary.getMostPopularDateOfMonth().get();
        LocalDate minUsageDate = usageReportSummary.getLeastPopularDateOfMonth().get();

        assertThat(maxUsageDate).isEqualTo(AN_ACCESS_LOG.getAccessDate());
        assertThat(minUsageDate).isEqualTo(AN_ACCESS_LOG_ONE_DAY_BEFORE.getAccessDate());
    }

    private List<ParkingAccessLog> generateOneAccessLogPerDay(int numberOfDays) {
        List<ParkingAccessLog> logs = new ArrayList<>();

        for (int dayNumber = 0; dayNumber < numberOfDays; dayNumber++) {
            ParkingAccessLog log = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now().minusDays(dayNumber));
            logs.add(log);
        }

        return logs;
    }
}
