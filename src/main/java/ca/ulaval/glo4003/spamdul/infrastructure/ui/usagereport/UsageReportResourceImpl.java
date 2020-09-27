package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportMonthDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;
import java.time.LocalDate;

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
    LocalDate reportStartDate = LocalDate.now();
    LocalDate reportEndDate = getLastDayOfMonthBeforeToday(reportStartDate);
    return usageReportService.getReportSummaryOfMonth(reportStartDate, reportEndDate);
  }

  @Override
  public UsageReportMonthDto getUsageReportMonth() {
    LocalDate reportStartDate = LocalDate.now();
    LocalDate reportEndDate = getLastDayOfMonthBeforeToday(reportStartDate);
    return usageReportService.getReportMonth(reportStartDate, reportEndDate);
  }

  private LocalDate getLastDayOfMonthBeforeToday(LocalDate date) {
    LocalDate now = LocalDate.now();
    LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());

    if (lastDayOfMonth.isAfter(now)) {
      return now;
    }

    return lastDayOfMonth;
  }
}
