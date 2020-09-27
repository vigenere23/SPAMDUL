package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportMonthDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;

public class UsageReportResourceImpl implements UsageReportResource {

  private final UsageReportService usageReportService;

  public UsageReportResourceImpl(UsageReportService usageReportService) {
    this.usageReportService = usageReportService;
  }

  @Override
  public UsageReportDto getUsageReport() {
    return null;
  }

  @Override
  public UsageReportSummaryDto getUsageReportSummary() {
    return usageReportService.getReportSummary();
  }

  @Override
  public UsageReportMonthDto getUsageReportMonth() {
    return usageReportService.getReportMonth();
  }
}
