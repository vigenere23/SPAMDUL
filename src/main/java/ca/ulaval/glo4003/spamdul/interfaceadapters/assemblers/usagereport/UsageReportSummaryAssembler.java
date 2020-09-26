package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.UsageReportSummary;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;

public class UsageReportSummaryAssembler {
    public UsageReportSummaryDto toDto(UsageReportSummary usageReportSummary) {
        UsageReportSummaryDto dto = new UsageReportSummaryDto();
        dto.usagePerDayMean = usageReportSummary.getUsagePerDayMean();
        dto.mostPopularMonthDay = usageReportSummary.getMostPopularDayOfMonth().getDayOfMonth();
        dto.leastPopularMonthDay = usageReportSummary.getLeastPopularDayOfMonth().getDayOfMonth();
        return dto;
    }
}
