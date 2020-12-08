package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.UsageReportAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogIdFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingaccesslog.InMemoryParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public abstract class UsageReportContext implements ResourceContext {

  private final ParkingAccessLogger parkingAccessLogger;
  private final UsageReportResource usageReportResource;

  public UsageReportContext(AuthenticationRepository authenticationRepository,
                            AccessTokenCookieAssembler cookieAssembler) {
    ParkingAccessLogRepository parkingAccessLogRepository = new InMemoryParkingAccessLogRepository();

    ParkingAccessLogAgglomerator parkingAccessLogAgglomerator = new ParkingAccessLogAgglomerator();

    UsageReportSummaryFactory usageReportSummaryFactory = new UsageReportSummaryFactory();
    UsageReportFactory usageReportFactory = new UsageReportFactory();
    ParkingAccessLogIdFactory parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(new IncrementalIdGenerator());
    ParkingAccessLogFactory parkingAccessLogFactory = new ParkingAccessLogFactory(parkingAccessLogIdFactory);

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

    usageReportResource = new UsageReportResource(usageReportService,
                                                  usageReportCreationAssembler,
                                                  usageReportSummaryCreationAssembler,
                                                  cookieAssembler);
    Populator populator = new ParkingAccessLogPopulator(parkingAccessLogRepository, parkingAccessLogFactory);

    this.populateData(populator);
  }

  public ParkingAccessLogger getParkingAccessLogger() {
    return parkingAccessLogger;
  }

  protected abstract void populateData(Populator populator);

  @Override public void registerResources(InstanceMap resources) {
    resources.add(usageReportResource);
    resources.add(new UsageReportExceptionAssembler());
  }
}
