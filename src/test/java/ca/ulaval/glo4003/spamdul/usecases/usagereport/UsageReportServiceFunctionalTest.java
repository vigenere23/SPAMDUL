package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonthFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportMonthAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UsageReportServiceFunctionalTest {

  private UsageReportService usageReportService;
  private ParkingAccessLogRepository parkingAccessLogRepository;

  @Before
  public void setUp() {
    parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
    usageReportService = new UsageReportService(
        parkingAccessLogRepository,
        new ParkingAccessLogFilter(),
        new ParkingAccessLogAgglomerator(),
        new UsageReportSummaryFactory(),
        new UsageReportSummaryAssembler(),
        new UsageReportMonthFactory(),
        new UsageReportMonthAssembler()
    );
  }

  @Test
  public void whenGettingUsageReportSummary_shouldReturnTheRightSummary() {
    LocalDate reportStartDate = LocalDate.of(2010, 1, 1);
    LocalDate reportEndDate = reportStartDate.withDayOfMonth(reportStartDate.lengthOfMonth());
    LocalDate mostPopularDate = reportStartDate.plusDays(15);
    LocalDate leastPopularDate = reportStartDate.plusDays(7);
    LocalDate randomDate = reportStartDate.plusDays(21);
    int numberOfMostPopularDateLogs = 13;
    int numberOfLeastPopularDateLogs = 5;
    int numberOfRandomLogs = 7;
    int numberOfMonthDays = reportStartDate.lengthOfMonth();
    float meanUsage =
        (float) (numberOfMostPopularDateLogs + numberOfLeastPopularDateLogs + numberOfRandomLogs) / numberOfMonthDays;
    createLogs(mostPopularDate, numberOfMostPopularDateLogs);
    createLogs(leastPopularDate, numberOfLeastPopularDateLogs);
    createLogs(randomDate, numberOfRandomLogs);

    UsageReportSummaryDto usageReportSummaryDto = usageReportService.getReportSummaryOfMonth(reportStartDate,
                                                                                             reportEndDate);

    assertThat(usageReportSummaryDto.mostPopularMonthDate).isEquivalentAccordingToCompareTo(mostPopularDate);
    assertThat(usageReportSummaryDto.leastPopularMonthDate).isEquivalentAccordingToCompareTo(leastPopularDate);
    assertThat(usageReportSummaryDto.meanUsagePerDay).isEqualTo(meanUsage);
  }

  private void createLogs(LocalDate date, int numberOfLogs) {
    for (int logNumber = 0; logNumber < numberOfLogs; logNumber++) {
      ParkingAccessLog log = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, date);
      parkingAccessLogRepository.save(log);
    }
  }
}
