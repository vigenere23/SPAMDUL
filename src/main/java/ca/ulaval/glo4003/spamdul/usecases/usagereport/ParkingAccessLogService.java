package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingAccessLogService {
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private ParkingAccessLogFilter parkingAccessLogFilter;
  private UsageReportSummaryAssembler usageReportSummaryAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogFilter parkingAccessLogFilter,
                                 UsageReportSummaryAssembler usageReportSummaryAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFilter = parkingAccessLogFilter;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
  }

  public UsageReportSummaryDto getReportSummary() {
    List<ParkingAccessLog> logs = parkingAccessLogRepository.findAllWithFilter(
            parkingAccessLogFilter.fromCurrentMonth()
    );
    LocalDate maxUsageDay = logs.fin
  }
}
