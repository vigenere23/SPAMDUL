package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonth;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UsageReportMonthDto {
//  public Map<LocalDate, Integer> UsageByDate;
  public List<UsageReportDayDto> usageByDate;
}
