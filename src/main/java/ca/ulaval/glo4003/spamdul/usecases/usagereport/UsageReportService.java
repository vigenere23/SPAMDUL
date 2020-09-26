package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportService {
  private final ParkingAccessLogRepository parkingAccessLogRepository;
  private final ParkingAccessLogFilter parkingAccessLogFilter;
  private final ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
  private final UsageReportSummaryFactory usageReportSummaryFactory;
  private final UsageReportSummaryAssembler usageReportSummaryAssembler;

  public UsageReportService(ParkingAccessLogRepository parkingAccessLogRepository,
                            ParkingAccessLogFilter parkingAccessLogFilter,
                            ParkingAccessLogAgglomerator parkingAccessLogAgglomerator,
                            UsageReportSummaryFactory usageReportSummaryFactory,
                            UsageReportSummaryAssembler usageReportSummaryAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    this.usageReportSummaryFactory = usageReportSummaryFactory;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
  }

  public UsageReportSummaryDto getReportSummary() {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
            .setData(allLogs)
            .fromCurrentMonth()
            .getResultsAndReset();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(lastMonthLogs);
    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(lastMonthLogsPerDay);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }
}
