package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonth;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonthFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportMonthAssembler;
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
  private final UsageReportMonthFactory usageReportMonthFactory;
  private final UsageReportMonthAssembler usageReportMonthAssembler;

  public UsageReportService(ParkingAccessLogRepository parkingAccessLogRepository,
                            ParkingAccessLogFilter parkingAccessLogFilter,
                            ParkingAccessLogAgglomerator parkingAccessLogAgglomerator,
                            UsageReportSummaryFactory usageReportSummaryFactory,
                            UsageReportSummaryAssembler usageReportSummaryAssembler,
                            UsageReportMonthFactory usageReportMonthFactory,
                            UsageReportMonthAssembler usageReportMonthAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    this.usageReportSummaryFactory = usageReportSummaryFactory;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
    this.usageReportMonthFactory = usageReportMonthFactory;
    this.usageReportMonthAssembler = usageReportMonthAssembler;
  }

  public UsageReportSummaryDto getReportSummary() {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
        .setData(allLogs)
        .fromOngoingMonth()
        .getResults();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        lastMonthLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(lastMonthLogsPerDay);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }

  public UsageReportMonthDto getReportMonth() {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
        .setData(allLogs)
        .fromOngoingMonth()
        .getResults();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        lastMonthLogs);

    UsageReportMonth usageReportMonth = usageReportMonthFactory.create(lastMonthLogsPerDay);

    return usageReportMonthAssembler.toDto(usageReportMonth);
  }
}
