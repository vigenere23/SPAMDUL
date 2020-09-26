package ca.ulaval.glo4003.spamdul.context;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.ParkingAccessLogFilterImpl;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.ParkingAccessLogService;

public class UsageReportContext {
    public UsageReportResource createUsageReportResource() {
        ParkingAccessLogRepository parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
        ParkingAccessLogFilter parkingAccessLogFilter = new ParkingAccessLogFilterImpl();
        ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
        UsageReportFactory usageReportFactory = new UsageReportFactory(parkingAccessLogAgglomerator);
        UsageReportSummaryAssembler usageReportSummaryAssembler = new UsageReportSummaryAssembler();

        ParkingAccessLogService parkingAccessLogService = new ParkingAccessLogService(
                parkingAccessLogRepository,
                parkingAccessLogFilter,
                usageReportFactory,
                usageReportSummaryAssembler
        );

        return new UsageReportResourceImpl(parkingAccessLogService);
    }
}
