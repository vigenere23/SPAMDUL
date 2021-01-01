package ca.ulaval.glo4003.spamdul.usage.context;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportSummaryAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.UsageReportAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usage.api.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogIdFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportFactory;
import ca.ulaval.glo4003.spamdul.usage.entities.usagereport.UsageReportSummaryFactory;
import ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog.InMemoryParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.UsageReportUseCase;

public abstract class UsageContext implements ResourceContext {

  private final ParkingAccessLogger parkingAccessLogger;
  private final UsageReportResource usageReportResource;

  public UsageContext(AuthenticationRepository authenticationRepository,
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

    parkingAccessLogger = new ParkingAccessLogger(parkingAccessLogFactory,
                                                  parkingAccessLogRepository);

    AccessLevelValidator accessLevelValidator = new UsageReportAccessLevelValidator(authenticationRepository);

    UsageReportUseCase usageReportUseCase = new UsageReportUseCase(parkingAccessLogRepository,
                                                                   parkingAccessLogAgglomerator,
                                                                   usageReportSummaryFactory,
                                                                   usageReportSummaryAssembler,
                                                                   usageReportFactory,
                                                                   usageReportAssembler,
                                                                   accessLevelValidator);

    usageReportResource = new UsageReportResource(usageReportUseCase,
                                                  usageReportCreationAssembler,
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
  }
}
