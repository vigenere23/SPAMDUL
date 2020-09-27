package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonth;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDayDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportMonthDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsageReportMonthAssembler {
  public UsageReportMonthDto toDto(UsageReportMonth usageReportMonth) {
    UsageReportMonthDto monthDto = new UsageReportMonthDto();
    List<UsageReportDayDto> usageReportDay = new ArrayList<>();

    for (Map.Entry<LocalDate, Integer> entry : usageReportMonth.getUsageByDate().entrySet()) {
      UsageReportDayDto dayDto = new UsageReportDayDto();
      dayDto.date = entry.getKey();
      dayDto.usage = entry.getValue();
      usageReportDay.add(dayDto);
    }
    monthDto.usageByDate = usageReportDay;

    return monthDto;
  }
}
