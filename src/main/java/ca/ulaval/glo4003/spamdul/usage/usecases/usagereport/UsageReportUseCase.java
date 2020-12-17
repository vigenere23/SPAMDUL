package ca.ulaval.glo4003.spamdul.usage.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReport;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportSummaryDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportUseCase {

  private final ParkingAccessLogRepository parkingAccessLogRepository;
  private final ParkingAccessLogAgglomerator parkingAccessLogAgglomerator;
  private final UsageReportSummaryFactory usageReportSummaryFactory;
  private final UsageReportSummaryAssembler usageReportSummaryAssembler;
  private final UsageReportFactory usageReportFactory;
  private final UsageReportAssembler usageReportAssembler;
  private final AccessLevelValidator accessLevelValidator;

  public UsageReportUseCase(ParkingAccessLogRepository parkingAccessLogRepository,
                            ParkingAccessLogAgglomerator parkingAccessLogAgglomerator,
                            UsageReportSummaryFactory usageReportSummaryFactory,
                            UsageReportSummaryAssembler usageReportSummaryAssembler,
                            UsageReportFactory usageReportFactory,
                            UsageReportAssembler usageReportAssembler,
                            AccessLevelValidator accessLevelValidator) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogAgglomerator = parkingAccessLogAgglomerator;
    this.usageReportSummaryFactory = usageReportSummaryFactory;
    this.usageReportSummaryAssembler = usageReportSummaryAssembler;
    this.usageReportFactory = usageReportFactory;
    this.usageReportAssembler = usageReportAssembler;
    this.accessLevelValidator = accessLevelValidator;
  }

  public UsageReportSummaryDto getReportSummary(UsageReportCreationDto usageReportCreationDto,
                                                TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Map<LocalDate, List<ParkingAccessLog>> logsPerDay = getLogsForReport(usageReportCreationDto.startDate,
                                                                         usageReportCreationDto.endDate,
                                                                         usageReportCreationDto.parkingZone,
                                                                         usageReportCreationDto.parkingCategory);

    UsageReportSummary usageReportSummary = usageReportSummaryFactory.create(logsPerDay,
                                                                             usageReportCreationDto.startDate,
                                                                             usageReportCreationDto.endDate,
                                                                             usageReportCreationDto.parkingZone,
                                                                             usageReportCreationDto.parkingCategory);

    return usageReportSummaryAssembler.toDto(usageReportSummary);
  }

  public UsageReportDto getReport(UsageReportCreationDto usageReportCreationDto,
                                  TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Map<LocalDate, List<ParkingAccessLog>> logsPerDay = getLogsForReport(usageReportCreationDto.startDate,
                                                                         usageReportCreationDto.endDate,
                                                                         usageReportCreationDto.parkingZone,
                                                                         usageReportCreationDto.parkingCategory);
    UsageReport usageReport = usageReportFactory.create(logsPerDay,
                                                        usageReportCreationDto.parkingZone,
                                                        usageReportCreationDto.parkingCategory);

    return usageReportAssembler.toDto(usageReport);
  }

  private Map<LocalDate, List<ParkingAccessLog>> getLogsForReport(LocalDate startDate,
                                                                  LocalDate endDate,
                                                                  ParkingZone parkingZone,
                                                                  ParkingCategory parkingCategory) {
    List<ParkingAccessLog> allLogs = parkingAccessLogRepository.findAll();
    ParkingAccessLogFilter parkingAccessLogFilter = new ParkingAccessLogFilter()
        .setData(allLogs)
        .betweenDates(startDate,
                      endDate)
        .atCategory(parkingCategory)
        .atZone(parkingZone);

    List<ParkingAccessLog> filteredLogs = parkingAccessLogFilter.getResults();

    return parkingAccessLogAgglomerator.groupByAccessDate(filteredLogs);
  }
}
