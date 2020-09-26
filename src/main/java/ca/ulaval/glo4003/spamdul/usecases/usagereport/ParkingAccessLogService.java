package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;

import java.util.List;

public class ParkingAccessLogService {
  private final ParkingAccessLogRepository parkingAccessLogRepository;
  private final ParkingAccessLogFilter parkingAccessLogFilter;
  private final UsageReportFactory usageReportFactory;
  private final UsageReportSummaryAssembler usageReportSummaryAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogFilter parkingAccessLogFilter,
                                 UsageReportFactory usageReportFactory,
                                 UsageReportSummaryAssembler usageReportSummaryAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.usageReportFactory = usageReportFactory;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
  }

  public UsageReportSummaryDto getReportSummary() {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
            .setData(allLogs)
            .fromCurrentMonth()
            .getResultsAndReset();
    UsageReportSummary usageReportSummary = usageReportFactory.createSummaryReport(lastMonthLogs);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }
}
