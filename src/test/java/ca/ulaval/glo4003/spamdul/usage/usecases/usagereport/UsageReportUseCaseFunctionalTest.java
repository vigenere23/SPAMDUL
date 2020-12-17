package ca.ulaval.glo4003.spamdul.usage.usecases.usagereport;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogIdFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog.InMemoryParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportSummaryDto;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportUseCaseFunctionalTest {

  private UsageReportUseCase usageReportUseCase;
  private ParkingAccessLogRepository parkingAccessLogRepository;

  private final LocalDate A_DATE = LocalDate.of(1995, 8, 13);
  private final LocalDate A_LATER_DATE = LocalDate.of(1995, 8, 20);
  private final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();
  private final ParkingAccessLogIdFactory parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(new IncrementalIdGenerator());
  private final ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory(parkingAccessLogIdFactory);

  @Mock
  private AccessLevelValidator accessLevelValidator;


  @Before
  public void setUp() {
    parkingAccessLogRepository = new InMemoryParkingAccessLogRepository();
    usageReportUseCase = new UsageReportUseCase(
        parkingAccessLogRepository,
        new ParkingAccessLogAgglomerator(),
        new UsageReportSummaryFactory(),
        new UsageReportSummaryAssembler(),
        new UsageReportFactory(),
        new UsageReportAssembler(),
        accessLevelValidator);
  }

  @Test
  public void whenGettingUsageReportSummary_shouldCallAccessLevelValidator() {
    LocalDate reportStartDate = LocalDate.of(2010, 1, 1);
    LocalDate reportEndDate = reportStartDate.plusDays(21);
    UsageReportCreationDto creationDto = new UsageReportCreationDto();
    creationDto.startDate = reportStartDate;
    creationDto.endDate = reportEndDate;

    usageReportUseCase.getReportSummary(creationDto, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingUsageReportSummary_shouldReturnTheRightSummary() {
    LocalDate reportStartDate = LocalDate.of(2010, 1, 1);
    LocalDate reportEndDate = reportStartDate.plusDays(21);
    LocalDate mostPopularDate = reportStartDate.plusDays(15);
    LocalDate leastPopularDate = reportStartDate.plusDays(7);
    LocalDate randomDate = reportStartDate.plusDays(9);
    UsageReportCreationDto creationDto = new UsageReportCreationDto();
    creationDto.startDate = reportStartDate;
    creationDto.endDate = reportEndDate;
    int numberOfMostPopularDateLogs = 13;
    int numberOfLeastPopularDateLogs = 5;
    int numberOfRandomLogs = 7;
    long numberOfMonthDays = ChronoUnit.DAYS.between(reportStartDate, reportEndDate) + 1;
    float meanUsage =
        (float) (numberOfMostPopularDateLogs + numberOfLeastPopularDateLogs + numberOfRandomLogs) / numberOfMonthDays;
    createLogs(mostPopularDate, numberOfMostPopularDateLogs);
    createLogs(leastPopularDate, numberOfLeastPopularDateLogs);
    createLogs(randomDate, numberOfRandomLogs);

    UsageReportSummaryDto usageReportSummaryDto = usageReportUseCase.getReportSummary(creationDto, A_TEMPORARY_TOKEN);

    assertThat(usageReportSummaryDto.mostPopularMonthDate).isEquivalentAccordingToCompareTo(mostPopularDate);
    assertThat(usageReportSummaryDto.leastPopularMonthDate).isEquivalentAccordingToCompareTo(leastPopularDate);
    assertThat(usageReportSummaryDto.meanUsagePerDay).isEqualTo(meanUsage);
  }

  @Test
  public void whenGettingUsageReport_shouldCallAccessLevelValidator() {
    Integer numberOfLogs = 12;
    UsageReportCreationDto usageReportCreationDto = new UsageReportCreationDto();
    usageReportCreationDto.startDate = A_DATE;
    usageReportCreationDto.endDate = A_LATER_DATE;

    createLogs(A_DATE, numberOfLogs);

    usageReportUseCase.getReport(usageReportCreationDto, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingUsageReport_shouldReturnTheRightUsageReport() {
    Integer numberOfLogs = 12;
    UsageReportCreationDto usageReportCreationDto = new UsageReportCreationDto();
    usageReportCreationDto.startDate = A_DATE;
    usageReportCreationDto.endDate = A_LATER_DATE;

    createLogs(A_DATE, numberOfLogs);

    UsageReportDto usageReportDto = usageReportUseCase.getReport(usageReportCreationDto, A_TEMPORARY_TOKEN);

    assertThat(usageReportDto.usageReport.get(0).date).isEqualTo(A_DATE);
    assertThat(usageReportDto.usageReport.get(0).numberOfEntry).isEqualTo(numberOfLogs);
    assertThat(usageReportDto.totalOfEntry).isEqualTo(numberOfLogs);
  }

  private void createLogs(LocalDate date, int numberOfLogs) {
    for (int logNumber = 0; logNumber < numberOfLogs; logNumber++) {
      ParkingAccessLog log = parkingAccessLogFactory.create(ParkingZone.ZONE_1, date);
      parkingAccessLogRepository.save(log);
    }
  }
}
