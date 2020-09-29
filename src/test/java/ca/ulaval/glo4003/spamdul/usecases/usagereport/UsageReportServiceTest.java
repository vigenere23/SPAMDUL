package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportServiceTest {

  private UsageReportService usageReportService;

  @Mock
  private ParkingAccessLogRepository parkingAccessLogRepository;
  @Mock
  private ParkingAccessLogFilter parkingAccessLogFilter;
  @Mock
  private ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
  @Mock
  private UsageReportSummaryFactory usageReportSummaryFactory;
  @Mock
  private UsageReportSummaryAssembler usageReportSummaryAssembler;
  @Mock
  private UsageReportFactory usageReportFactory;
  @Mock
  private UsageReportAssembler usageReportAssembler;
  @Mock
  private UsageReportSummary A_USAGE_REPORT_SUMMARY;

  private final LocalDate startDate = LocalDate.of(2010, 1, 1);
  private final LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

  @Before
  public void setUp() {
    usageReportService = new UsageReportService(
        parkingAccessLogRepository,
        parkingAccessLogFilter,
        parkingAccessLogAgglomerator,
        usageReportSummaryFactory,
        usageReportSummaryAssembler,
        usageReportFactory,
        usageReportAssembler
    );
  }

  @Test
  public void whenGettingSummaryReport_itCallsTheNeededDependencies() {
    List<ParkingAccessLog> returnedByRepo = new ArrayList<>();
    List<ParkingAccessLog> returnedByFilter = new ArrayList<>();
    Map<LocalDate, List<ParkingAccessLog>> returnedByAgglomerator = new HashMap<>();
    UsageReportSummary returnedByFactory = A_USAGE_REPORT_SUMMARY;
    when(parkingAccessLogRepository.findAll()).thenReturn(returnedByRepo);
    when(parkingAccessLogFilter.setData(returnedByRepo)).thenReturn(parkingAccessLogFilter);
    when(parkingAccessLogFilter.betweenDates(startDate, endDate)).thenReturn(parkingAccessLogFilter);
    when(parkingAccessLogFilter.getResults()).thenReturn(returnedByFilter);
    when(parkingAccessLogAgglomerator.groupByAccessDate(returnedByFilter)).thenReturn(returnedByAgglomerator);
    when(usageReportSummaryFactory.create(returnedByAgglomerator, startDate, endDate)).thenReturn(returnedByFactory);

    usageReportService.getReportSummary(startDate, endDate);

    verify(parkingAccessLogRepository).findAll();
    verify(parkingAccessLogFilter).setData(returnedByRepo);
    verify(parkingAccessLogFilter).betweenDates(startDate, endDate);
    verify(parkingAccessLogFilter).getResults();
    verify(parkingAccessLogFilter).setData(returnedByRepo);
    verify(parkingAccessLogAgglomerator).groupByAccessDate(returnedByFilter);
    verify(usageReportSummaryFactory).create(returnedByAgglomerator, startDate, endDate);
    verify(usageReportSummaryAssembler).toDto(returnedByFactory);
  }
}
