package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReport;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.RequestReportDto;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
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
  private final UsageReportFactory usageReportFactory;
  private final UsageReportAssembler usageReportAssembler;

  public UsageReportService(ParkingAccessLogRepository parkingAccessLogRepository,
                            ParkingAccessLogFilter parkingAccessLogFilter,
                            ParkingAccessLogAgglomerator parkingAccessLogAgglomerator,
                            UsageReportSummaryFactory usageReportSummaryFactory,
                            UsageReportSummaryAssembler usageReportSummaryAssembler,
                            UsageReportFactory usageReportFactory,
                            UsageReportAssembler usageReportAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    this.usageReportSummaryFactory = usageReportSummaryFactory;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
    this.usageReportFactory = usageReportFactory;
    this.usageReportAssembler = usageReportAssembler;
  }

  public UsageReportSummaryDto getReportSummaryOfMonth(LocalDate reportStartDate, LocalDate reportEndDate) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
        .setData(allLogs)
        .betweenDates(reportStartDate, reportEndDate)
        .getResults();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        lastMonthLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(lastMonthLogsPerDay,
                                                                             reportStartDate,
                                                                             reportEndDate);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }

  public UsageReportDto getReportMonth(LocalDate reportStartDate, LocalDate reportEndDate) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
        .setData(allLogs)
        .betweenDates(reportStartDate, reportEndDate)
        .getResults();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        lastMonthLogs);

    UsageReport usageReport = usageReportFactory.create(lastMonthLogsPerDay);

    return usageReportAssembler.toDto(usageReport);
  }

  public UsageReportDto getReport(RequestReportDto requestReportDTO) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    parkingAccessLogFilter.setData(allLogs);
    if (requestReportDTO.endDate == null) {
      parkingAccessLogFilter.atDate(requestReportDTO.startDate);
    } else {
      parkingAccessLogFilter.betweenDates(requestReportDTO.startDate, requestReportDTO.endDate);
    }
    if (requestReportDTO.parkingZone !=  null) {
      parkingAccessLogFilter.atZone(requestReportDTO.parkingZone);
    }
    List<ParkingAccessLog> logs = parkingAccessLogFilter.getResults();
    Map<LocalDate, List<ParkingAccessLog>> LogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(logs);

    UsageReport usageReport = usageReportFactory.create(LogsPerDay, requestReportDTO.parkingZone);

    return usageReportAssembler.toDto(usageReport);
  }



}
