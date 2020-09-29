package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.junit.Before;
import org.junit.Test;

public class UsageReportServiceFunctionalTest {

  private UsageReportService usageReportService;
  private ParkingAccessLogRepository parkingAccessLogRepository;

  private final LocalDate A_DATE = LocalDate.of(1995, 8, 13);
  private final LocalDate A_LATER_DATE = LocalDate.of(1995, 8, 20);

  @Before
  public void setUp() {
    parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
    usageReportService = new UsageReportService(
        parkingAccessLogRepository,
        new ParkingAccessLogFilter(),
        new ParkingAccessLogAgglomerator(),
        new UsageReportSummaryFactory(),
        new UsageReportSummaryAssembler(),
        new UsageReportFactory(),
        new UsageReportAssembler()
    );
  }

  @Test
  public void whenGettingUsageReportSummary_shouldReturnTheRightSummary() {
    LocalDate reportStartDate = LocalDate.of(2010, 1, 1);
    LocalDate reportEndDate = reportStartDate.plusDays(21);
    LocalDate mostPopularDate = reportStartDate.plusDays(15);
    LocalDate leastPopularDate = reportStartDate.plusDays(7);
    LocalDate randomDate = reportStartDate.plusDays(9);
    int numberOfMostPopularDateLogs = 13;
    int numberOfLeastPopularDateLogs = 5;
    int numberOfRandomLogs = 7;
    long numberOfMonthDays = ChronoUnit.DAYS.between(reportStartDate, reportEndDate) + 1;
    float meanUsage =
        (float) (numberOfMostPopularDateLogs + numberOfLeastPopularDateLogs + numberOfRandomLogs) / numberOfMonthDays;
    createLogs(mostPopularDate, numberOfMostPopularDateLogs);
    createLogs(leastPopularDate, numberOfLeastPopularDateLogs);
    createLogs(randomDate, numberOfRandomLogs);

    UsageReportSummaryDto usageReportSummaryDto = usageReportService.getReportSummary(reportStartDate,
                                                                                      reportEndDate);

    assertThat(usageReportSummaryDto.mostPopularMonthDate).isEquivalentAccordingToCompareTo(mostPopularDate);
    assertThat(usageReportSummaryDto.leastPopularMonthDate).isEquivalentAccordingToCompareTo(leastPopularDate);
    assertThat(usageReportSummaryDto.meanUsagePerDay).isEqualTo(meanUsage);
  }

  @Test
  public void whenGettingUsageReport_shouldReturnTheRightUsageReport() {
    // TODO add better testing
    Integer numberOfLogs = 12;

    ReportCreationDto reportCreationDto = new ReportCreationDto();
    reportCreationDto.startDate = A_DATE;

    createLogs(A_DATE, numberOfLogs);

    UsageReportDto usageReportDto = usageReportService.getReport(reportCreationDto);

    assertThat(usageReportDto.usageReport.get(0).date).isEqualTo(A_DATE);
    assertThat(usageReportDto.usageReport.get(0).numberOfEntry).isEqualTo(numberOfLogs);
  }

  private void createLogs(LocalDate date, int numberOfLogs) {
    for (int logNumber = 0; logNumber < numberOfLogs; logNumber++) {
      ParkingAccessLog log = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, date);
      parkingAccessLogRepository.save(log);
    }
  }
}
