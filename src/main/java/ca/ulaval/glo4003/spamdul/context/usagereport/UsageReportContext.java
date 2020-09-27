package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.*;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportMonthFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportMonthAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;

public class UsageReportContext {
    public UsageReportResource createUsageReportResource() {
        ParkingAccessLogRepository parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
        ParkingAccessLogFilter parkingAccessLogFilter = new ParkingAccessLogFilter();
        ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
        UsageReportSummaryFactory usageReportSummaryFactory = new UsageReportSummaryFactory();
        UsageReportSummaryAssembler usageReportSummaryAssembler = new UsageReportSummaryAssembler();
        UsageReportMonthFactory usageReportMonthFactory = new UsageReportMonthFactory();
        UsageReportMonthAssembler usageReportMonthAssembler = new UsageReportMonthAssembler();

        UsageReportService usageReportService = new UsageReportService(
            parkingAccessLogRepository,
            parkingAccessLogFilter,
            parkingAccessLogAgglomerator,
            usageReportSummaryFactory,
            usageReportSummaryAssembler,
            usageReportMonthFactory, usageReportMonthAssembler);

        ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory();
        ParkingAccessLogPopulator parkingAccessLogPopulator = new ParkingAccessLogPopulator(
                parkingAccessLogRepository, parkingAccessLogFactory
        );
        final int NUMBER_OF_ACCESS_LOGS = 300;
        parkingAccessLogPopulator.populate(NUMBER_OF_ACCESS_LOGS);

        return new UsageReportResourceImpl(usageReportService);
    }
}
