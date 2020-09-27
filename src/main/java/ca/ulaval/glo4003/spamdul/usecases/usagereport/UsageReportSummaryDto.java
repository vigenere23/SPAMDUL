package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import java.time.LocalDate;

public class UsageReportSummaryDto {

  public float meanUsagePerDay;
  public LocalDate mostPopularMonthDate;
  public LocalDate leastPopularMonthDate;
}
