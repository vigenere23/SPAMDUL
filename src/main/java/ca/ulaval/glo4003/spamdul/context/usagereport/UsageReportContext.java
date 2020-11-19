package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.UsageReportAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.ParkingAccessLogRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;

public class UsageReportContext {

  private final ParkingAccessLogPopulator parkingAccessLogPopulator;
  private final ParkingAccessLogger parkingAccessLogger;
  private final UsageReportResource usageReportResource;

  public UsageReportContext(AuthenticationRepository authenticationRepository,
                            AccessTokenCookieAssembler cookieAssembler,
                            boolean populateData) {
    ParkingAccessLogRepository parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();

    ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();

    UsageReportSummaryFactory usageReportSummaryFactory = new UsageReportSummaryFactory();
    UsageReportFactory usageReportFactory = new UsageReportFactory();
    ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory();

    UsageReportSummaryAssembler usageReportSummaryAssembler = new UsageReportSummaryAssembler();
    UsageReportAssembler usageReportAssembler = new UsageReportAssembler();
    UsageReportCreationAssembler usageReportCreationAssembler = new UsageReportCreationAssembler();
    UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler = new UsageReportSummaryCreationAssembler();

    parkingAccessLogger = new ParkingAccessLogger(parkingAccessLogFactory,
                                                  parkingAccessLogRepository);

    AccessLevelValidator accessLevelValidator = new UsageReportAccessLevelValidator(authenticationRepository);

    UsageReportService usageReportService = new UsageReportService(parkingAccessLogRepository,
                                                                   parkingAccessLogAgglomerator,
                                                                   usageReportSummaryFactory,
                                                                   usageReportSummaryAssembler,
                                                                   usageReportFactory,
                                                                   usageReportAssembler,
                                                                   accessLevelValidator);

    parkingAccessLogPopulator = new ParkingAccessLogPopulator(parkingAccessLogRepository,
                                                              parkingAccessLogFactory);

    usageReportResource = new UsageReportResourceImpl(usageReportService,
                                                      usageReportCreationAssembler,
                                                      usageReportSummaryCreationAssembler,
                                                      cookieAssembler);

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
