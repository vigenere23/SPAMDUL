package ca.ulaval.glo4003.spamdul.entity.usagereport;

import java.time.LocalDate;
import java.util.Map;

public class UsageReportMonth {

  private Map<LocalDate, Integer> usageByDate;

  public UsageReportMonth(Map<LocalDate, Integer> usageByDate) {
    this.usageByDate = usageByDate;
  }

  public Map<LocalDate, Integer> getUsageByDate() {
    return usageByDate;
  }
}
