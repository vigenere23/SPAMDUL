package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.RequestReportAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;

public class UsageReportContext {

  public UsageReportResource createUsageReportResource() {
    ParkingAccessLogRepository parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
    ParkingAccessLogFilter parkingAccessLogFilter = new ParkingAccessLogFilter();
    ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
    UsageReportSummaryFactory usageReportSummaryFactory = new UsageReportSummaryFactory();
    UsageReportFactory usageReportFactory = new UsageReportFactory();
    UsageReportSummaryAssembler usageReportSummaryAssembler = new UsageReportSummaryAssembler();
    UsageReportAssembler usageReportAssembler = new UsageReportAssembler();
    RequestReportAssembler requestReportAssembler = new RequestReportAssembler();


    UsageReportService usageReportService = new UsageReportService(
        parkingAccessLogRepository,
        parkingAccessLogFilter,
        parkingAccessLogAgglomerator,
        usageReportSummaryFactory,
        usageReportSummaryAssembler,
        usageReportFactory,
        usageReportAssembler);

    ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory();
    ParkingAccessLogPopulator parkingAccessLogPopulator = new ParkingAccessLogPopulator(
        parkingAccessLogRepository, parkingAccessLogFactory
    );
    final int NUMBER_OF_ACCESS_LOGS = 300;
    parkingAccessLogPopulator.populate(NUMBER_OF_ACCESS_LOGS);

    return new UsageReportResourceImpl(usageReportService, requestReportAssembler);
  }
}
