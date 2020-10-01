package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import java.time.LocalDate;

public class UsageReportSummaryDto {

  public float meanUsagePerDay;
  public LocalDate mostPopularMonthDate;
  public LocalDate leastPopularMonthDate;

  @Override
  public String toString() {
    return this.getClass().getName() + "\n{\n  meanUsagePerDay = " + meanUsagePerDay + "\n  mostPopularMonthDate = "
        + mostPopularMonthDate + "\n  leastPopularMonthDate = " + leastPopularMonthDate + "\n}";
  }
}
