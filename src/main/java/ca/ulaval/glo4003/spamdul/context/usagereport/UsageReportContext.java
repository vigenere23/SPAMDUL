package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.RequestReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;

public class UsageReportContext {

  private final ParkingAccessLogPopulator parkingAccessLogPopulator;
  private final ParkingAccessLogger parkingAccessLogger;
  private final UsageReportResource usageReportResource;

  public UsageReportContext(boolean populateData) {
    ParkingAccessLogFilter parkingAccessLogFilter = new ParkingAccessLogFilter();
    ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();
    UsageReportSummaryFactory usageReportSummaryFactory = new UsageReportSummaryFactory();
    UsageReportFactory usageReportFactory = new UsageReportFactory();
    UsageReportSummaryAssembler usageReportSummaryAssembler = new UsageReportSummaryAssembler();
    UsageReportAssembler usageReportAssembler = new UsageReportAssembler();
    RequestReportAssembler requestReportAssembler = new RequestReportAssembler();
    ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory();
    ParkingAccessLogRepository parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();
    parkingAccessLogger = new ParkingAccessLogger(parkingAccessLogFactory, parkingAccessLogRepository);

    UsageReportService usageReportService = new UsageReportService(
        parkingAccessLogRepository,
        parkingAccessLogFilter,
        parkingAccessLogAgglomerator,
        usageReportSummaryFactory,
        usageReportSummaryAssembler,
        usageReportFactory,
        usageReportAssembler);
    parkingAccessLogPopulator = new ParkingAccessLogPopulator(
        parkingAccessLogRepository, parkingAccessLogFactory
    );
    usageReportResource = new UsageReportResourceImpl(usageReportService, requestReportAssembler);

    if (populateData) {
      this.populateData();
    }
  }

  public UsageReportResource getUsageReportResource() {
    return usageReportResource;
  }

  public ParkingAccessLogger getParkingAccessLogger() {
    return parkingAccessLogger;
  }

  private void populateData() {
    final int NUMBER_OF_ACCESS_LOGS = 300;
    parkingAccessLogPopulator.populate(NUMBER_OF_ACCESS_LOGS);
  }
}
