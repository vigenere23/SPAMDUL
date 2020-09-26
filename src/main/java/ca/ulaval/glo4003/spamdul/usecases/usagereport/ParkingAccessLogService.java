package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ParkingAccessLogService {
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private ParkingAccessLogFilter parkingAccessLogFilter;
  private ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
  private UsageReportSummaryAssembler usageReportSummaryAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogFilter parkingAccessLogFilter,
                                 ParkingAccessLogAgglomerator parkingAccessLogAgglomerator,
                                 UsageReportSummaryAssembler usageReportSummaryAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
    this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
  }

  public UsageReportSummaryDto getReportSummary() {
    List<ParkingAccessLog> logs = parkingAccessLogRepository.findAllWithFilter(
            parkingAccessLogFilter.fromCurrentMonth()
    );
    Map<LocalDate, List<ParkingAccessLog>> logsPerDay = parkingAccessLogAgglomerator.groupPerDay(logs);
    float totalUsage = 0;
    int minNumberOfAccesses = Integer.MAX_VALUE;
    int maxNumberOfAccesses = 0;
    LocalDate dayWithMinUsage = null;
    LocalDate dayWithMaxUsage = null;
    for (Map.Entry<LocalDate, List<ParkingAccessLog>> entry : logsPerDay.entrySet()) {
      int numberOfAccesses = entry.getValue().size();
      totalUsage += numberOfAccesses;

      if (numberOfAccesses > maxNumberOfAccesses) {
        maxNumberOfAccesses = numberOfAccesses;
        dayWithMaxUsage = entry.getKey();
      }

      if (numberOfAccesses < minNumberOfAccesses) {
        minNumberOfAccesses = numberOfAccesses;
        dayWithMinUsage = entry.getKey();
      }
    }

    float meanUsage = totalUsage / logsPerDay.keySet().size();

    UsageReportSummary usageReportSummary = new UsageReportSummary(meanUsage, dayWithMaxUsage, dayWithMinUsage);
    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }
}
