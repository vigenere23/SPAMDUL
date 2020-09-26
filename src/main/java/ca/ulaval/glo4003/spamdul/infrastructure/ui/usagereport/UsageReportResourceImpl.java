package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.usecases.usagereport.ParkingAccessLogService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;

public class UsageReportResourceImpl implements UsageReportResource {

    private final ParkingAccessLogService parkingAccessLogService;

    public UsageReportResourceImpl(ParkingAccessLogService parkingAccessLogService) {
        this.parkingAccessLogService = parkingAccessLogService;
    }

    @Override
    public UsageReportDto getUsageReport() {
        return null;
    }

    @Override
    public UsageReportSummaryDto getUsageReportSummary() {
        return parkingAccessLogService.getReportSummary();
    }
}
