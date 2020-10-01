package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReport;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;
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

  public UsageReportSummaryDto getReportSummary(UsageReportSummaryCreationDto usageReportSummaryCreationDto) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    List<ParkingAccessLog> lastMonthLogs = parkingAccessLogFilter
        .setData(allLogs)
        .betweenDates(usageReportSummaryCreationDto.startDate, usageReportSummaryCreationDto.endDate)
        .getResults();
    Map<LocalDate, List<ParkingAccessLog>> lastMonthLogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(
        lastMonthLogs);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(lastMonthLogsPerDay,
                                                                             usageReportSummaryCreationDto.startDate,
                                                                             usageReportSummaryCreationDto.endDate);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }

  public UsageReportDto getReport(UsageReportCreationDto usageReportCreationDto) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    parkingAccessLogFilter.setData(allLogs)
                          .betweenDates(usageReportCreationDto.startDate, usageReportCreationDto.endDate);

    if (usageReportCreationDto.parkingZone != null) {
      parkingAccessLogFilter.atZone(usageReportCreationDto.parkingZone);
    }

    List<ParkingAccessLog> logs = parkingAccessLogFilter.getResults();
    Map<LocalDate, List<ParkingAccessLog>> LogsPerDay = parkingAccessLogAgglomerator.groupByAccessDate(logs);

    UsageReport usageReport = usageReportFactory.create(LogsPerDay, usageReportCreationDto.parkingZone);

    return usageReportAssembler.toDto(usageReport);
  }
}
