package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;

public class UsageReportSummaryAssembler {
    public UsageReportSummaryDto toDto(UsageReportSummary usageReportSummary) {
        UsageReportSummaryDto dto = new UsageReportSummaryDto();

        dto.meanUsagePerDay = usageReportSummary.getMeanUsagePerDay();
        usageReportSummary.getMostPopularDayOfMonth().ifPresent(localDate -> dto.mostPopularMonthDay = localDate.getDayOfMonth());
        usageReportSummary.getLeastPopularDayOfMonth().ifPresent(localDate -> dto.leastPopularMonthDay = localDate.getDayOfMonth());

        return dto;
    }
}
