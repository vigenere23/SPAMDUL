package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;

public class UsageReportResourceImpl implements UsageReportResource {

  private final UsageReportService usageReportService;
  private final UsageReportCreationAssembler usageReportCreationAssembler;
  private final UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler;

  public UsageReportResourceImpl(UsageReportService usageReportService,
                                 UsageReportCreationAssembler usageReportCreationAssembler,
                                 UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler) {
    this.usageReportService = usageReportService;
    this.usageReportCreationAssembler = usageReportCreationAssembler;
    this.usageReportSummaryCreationAssembler = usageReportSummaryCreationAssembler;
  }

  @Override
  public UsageReportDto getUsageReport(String startDate, String endDate, String parkingZone) {
    UsageReportCreationDto creationDto = usageReportCreationAssembler.fromValues(startDate,
                                                                                 endDate,
                                                                                 parkingZone);
    return usageReportService.getReport(creationDto);
  }

  @Override
  public UsageReportSummaryDto getUsageReportSummary(String startDate, String endDate) {
    UsageReportSummaryCreationDto creationDto = usageReportSummaryCreationAssembler.fromValues(startDate, endDate);
    return usageReportService.getReportSummary(creationDto);
  }
}
