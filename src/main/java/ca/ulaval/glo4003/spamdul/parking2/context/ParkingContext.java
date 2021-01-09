package ca.ulaval.glo4003.spamdul.parking2.context;

import ca.ulaval.glo4003.spamdul.account.entities.AccountService;
import ca.ulaval.glo4003.spamdul.billing.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.billing.api.BillingUriBuilder;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.parking2.api.ParkingResource;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessPeriodCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.CarDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.InfractionDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.CarCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.PermitDeliveryAssembler;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactoryDayPerWeek;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactoryHourly;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactoryMonth;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactorySemester;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodFactorySingleDay;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociationQueue;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightFilterStrategyDate;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightFilterStrategyParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightFilterStrategyTime;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightValidator;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightValidatorFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAssociationQueue;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionCreator;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionIdFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfosRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionTypeFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.association.PermitAssociationQueue;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.association.PermitAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryBike;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryCar;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitNumberFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.InfractionInfosRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.ParkingCarFeeRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.ParkingUserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.ParkingZoneFeeRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.InfractionInfosPopulator;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.InfractionInfosPopulatorJson;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.ParkingCarFeePopulator;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.ParkingCarFeePopulatorCsv;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.ParkingZoneFeePopulator;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators.ParkingZoneFeePopulatorCsv;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessRightUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingPermitUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUserUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessRightsAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.CarAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.CarCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.DeliveryInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.ParkingUserAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitsAssembler;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;

public class ParkingContext implements ResourceContext {

  private final ParkingResource parkingResource;

  public ParkingContext(InvoiceCreator invoiceCreator,
                        InvoicePaidObservable invoicePaidObservable,
                        BillingUriBuilder billingUriBuilder,
                        AccountService accountService) {
    Calendar calendar = new HardCodedCalendar();
    ParkingUserRepository parkingUserRepository = new ParkingUserRepositoryInMemory();

    ParkingZoneFeeRepository zoneFeeRepository = new ParkingZoneFeeRepositoryInMemory();
    ParkingZoneFeePopulator zoneFeePopulator = new ParkingZoneFeePopulatorCsv(new CsvReader(),
                                                                              "src/main/resources/frais-zone.csv");
    zoneFeePopulator.populate(zoneFeeRepository);

    ParkingCarFeeRepository carFeeRepository = new ParkingCarFeeRepositoryInMemory();
    ParkingCarFeePopulator parkingCarFeePopulator = new ParkingCarFeePopulatorCsv(new CsvReader(),
                                                                                  "src/main/resources/frais-acces.csv");
    parkingCarFeePopulator.populate(carFeeRepository);

    InfractionInfosRepository infractionInfosRepository = new InfractionInfosRepositoryInMemory();
    InfractionInfosPopulator infractionInfosPopulator = new InfractionInfosPopulatorJson(new JsonReader(),
                                                                                         "src/main/resources/infraction.json");
    infractionInfosPopulator.populate(infractionInfosRepository);

    PermitAssociator permitAssociator = new PermitAssociator(parkingUserRepository);
    PermitAssociationQueue permitAssociationQueue = new PermitAssociationQueue(permitAssociator);
    invoicePaidObservable.register(permitAssociationQueue);

    AccessRightAssociator accessRightAssociator = new AccessRightAssociator(parkingUserRepository);
    AccessRightAssociationQueue accessRightAssociationQueue = new AccessRightAssociationQueue(accessRightAssociator);
    invoicePaidObservable.register(accessRightAssociationQueue);

    InfractionAssociator infractionAssociator = new InfractionAssociator(parkingUserRepository);
    InfractionAssociationQueue infractionAssociationQueue = new InfractionAssociationQueue(infractionAssociator);
    invoicePaidObservable.register(infractionAssociationQueue);

    AccessRightValidator accessRightValidator = new AccessRightValidatorFactory().create(
        new AccessRightFilterStrategyParkingZone(),
        new AccessRightFilterStrategyDate(),
        new AccessRightFilterStrategyTime()
    );
    ParkingUserFactory parkingUserFactory = new ParkingUserFactory();
    PermitNumberFactory permitNumberFactory = new PermitNumberFactory(new IncrementalIdGenerator());
    PermitFactoryCar permitFactoryCar = new PermitFactoryCar(permitNumberFactory,
                                                             accessRightValidator,
                                                             new CarFactory());
    PermitFactoryBike permitFactoryBike = new PermitFactoryBike(permitNumberFactory);
    PermitFactory permitFactory = new PermitFactory(permitFactoryCar, permitFactoryBike);
    AccessRightFactory accessRightFactory = new AccessRightFactory(new AccessPeriodFactory(new AccessPeriodFactoryHourly(),
                                                                                           new AccessPeriodFactorySingleDay(),
                                                                                           new AccessPeriodFactoryDayPerWeek(
                                                                                               calendar),
                                                                                           new AccessPeriodFactorySemester(
                                                                                               calendar),
                                                                                           new AccessPeriodFactoryMonth()));
    InfractionFactory infractionFactory = new InfractionFactory(new InfractionIdFactory(new IncrementalIdGenerator()));
    InfractionCreator infractionCreator = new InfractionCreator(new InfractionTypeFactory(),
                                                                infractionFactory,
                                                                infractionInfosRepository);

    ParkingUserAssembler parkingUserAssembler = new ParkingUserAssembler(new PermitsAssembler(new AccessRightsAssembler(),
                                                                                              new CarAssembler()));
    ParkingUserUseCase parkingUserUseCase = new ParkingUserUseCase(parkingUserRepository,
                                                                   parkingUserFactory,
                                                                   parkingUserAssembler, accountService);
    ParkingPermitUseCase parkingPermitUseCase = new ParkingPermitUseCase(parkingUserRepository,
                                                                         permitFactory,
                                                                         new DeliveryInfosAssembler(),
                                                                         new PermitCreationInfosAssembler(
                                                                             new CarCreationInfosAssembler()),
                                                                         invoiceCreator,
                                                                         permitAssociationQueue);
    ParkingAccessRightUseCase parkingAccessRightUseCase = new ParkingAccessRightUseCase(parkingUserRepository,
                                                                                        zoneFeeRepository,
                                                                                        carFeeRepository,
                                                                                        accessRightFactory,
                                                                                        new AccessPeriodCreationInfosAssembler(),
                                                                                        invoiceCreator,
                                                                                        accessRightAssociationQueue);
    ParkingAccessUseCase parkingAccessUseCase = new ParkingAccessUseCase(parkingUserRepository, infractionCreator,
                                                                         invoiceCreator, infractionAssociationQueue,
                                                                         new InfractionAssembler());

    UserCreationAssembler userCreationAssembler = new UserCreationAssembler();
    PermitCreationAssembler permitCreationAssembler = new PermitCreationAssembler(new PermitDeliveryAssembler(),
                                                                                  new CarCreationAssembler());
    AccessRightCreationAssembler accessRightCreationAssembler = new AccessRightCreationAssembler(new AccessPeriodCreationAssembler());
    ParkingAccessAssembler parkingAccessAssembler = new ParkingAccessAssembler();
    parkingResource = new ParkingResource(userCreationAssembler,
                                          permitCreationAssembler,
                                          accessRightCreationAssembler,
                                          parkingAccessAssembler,
                                          parkingUserUseCase,
                                          parkingPermitUseCase,
                                          parkingAccessRightUseCase,
                                          parkingAccessUseCase,
                                          new ParkingUserDtoAssembler(new PermitDtoAssembler(new CarDtoAssembler(),
                                                                                             new AccessRightDtoAssembler())),
                                          new InfractionDtoAssembler(),
                                          billingUriBuilder);
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(parkingResource);
    // TODO add exception mapper
  }
}
