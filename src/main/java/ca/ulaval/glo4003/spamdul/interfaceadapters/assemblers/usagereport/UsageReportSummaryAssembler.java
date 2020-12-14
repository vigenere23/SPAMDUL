package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;

public class UsageReportSummaryAssembler {

  public UsageReportSummaryDto toDto(UsageReportSummary usageReportSummary) {
    UsageReportSummaryDto dto = new UsageReportSummaryDto();

    dto.meanUsagePerDay = usageReportSummary.getMeanUsagePerDay();
    usageReportSummary.getMostPopularDateOfMonth()
                      .ifPresent(mostPopularDate -> dto.mostPopularMonthDate = mostPopularDate);
    usageReportSummary.getLeastPopularDateOfMonth()
                      .ifPresent(leastPopularDate -> dto.leastPopularMonthDate = leastPopularDate);
    usageReportSummary.getParkingZone().ifPresent(parkingZone -> dto.parkingZone = parkingZone);
    usageReportSummary.getParkingCategory().ifPresent(parkingCategory -> dto.parkingCategory = parkingCategory);

    return dto;
  }
}
