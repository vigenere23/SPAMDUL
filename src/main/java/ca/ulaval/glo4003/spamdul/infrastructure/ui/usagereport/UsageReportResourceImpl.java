package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.RequestReportAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;
import java.time.LocalDate;

public class UsageReportResourceImpl implements UsageReportResource {

  private final UsageReportService usageReportService;
  private final RequestReportAssembler requestReportAssembler;

  public UsageReportResourceImpl(UsageReportService usageReportService,
                                 RequestReportAssembler requestReportAssembler) {
    this.usageReportService = usageReportService;
    this.requestReportAssembler = requestReportAssembler;
  }

  @Override
  public UsageReportDto getUsageReport(ReportRequest reportRequest) {
    ReportCreationDto reportCreationDTO = requestReportAssembler.fromDto(reportRequest);
    return usageReportService.getReport(reportCreationDTO);
  }

  @Override
  public UsageReportSummaryDto getUsageReportSummary() {
    LocalDate reportEndDate = LocalDate.now();
    LocalDate reportStartDate = reportEndDate.withDayOfMonth(1);
    return usageReportService.getReportSummary(reportStartDate, reportEndDate);
  }

  @Override
  public UsageReportDto getUsageReportMonth() {
    LocalDate reportEndDate = LocalDate.now();
    LocalDate reportStartDate = reportEndDate.withDayOfMonth(1);
    return usageReportService.getReportMonth(reportStartDate, reportEndDate);
  }
}
