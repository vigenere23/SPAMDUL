package ca.ulaval.glo4003.spamdul.parking2.context;

import ca.ulaval.glo4003.spamdul.invoice.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.invoice.api.InvoiceUriBuilder;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.parking2.api.ParkingResource;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessPeriodCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.CarDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.CarCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.PermitDeliveryAssembler;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactoryDayPerWeek;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactoryHourly;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactoryMonth;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactorySession;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactorySingleDay;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFilterDate;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFilterParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFilterTime;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightValidator;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightValidatorFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAmountRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionCreator;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionIdFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionTypeFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryBike;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryCar;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryProvider;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitNumberFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserIdFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.InfractionAmountRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.ParkingUserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessRightUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingPermitUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUserUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessRightsAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.CarAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.CarCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.DeliveryInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.ParkingUserAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitsAssembler;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;

public class ParkingContext implements ResourceContext {

  private final ParkingResource parkingResource;

  public ParkingContext(InvoiceCreator invoiceCreator,
                        InvoicePaidObservable invoicePaidObservable,
                        InvoiceUriBuilder invoiceUriBuilder) {
    Calendar calendar = new HardCodedCalendar();
    ParkingUserRepository parkingUserRepository = new ParkingUserRepositoryInMemory();
    InfractionAmountRepository infractionAmountRepository = new InfractionAmountRepositoryInMemory();
    // TODO populate infractionAmountRepository

    PermitAssociator permitAssociator = new PermitAssociator(parkingUserRepository);
    AccessRightAssociator accessRightAssociator = new AccessRightAssociator(parkingUserRepository);
    invoicePaidObservable.register(permitAssociator);
    invoicePaidObservable.register(accessRightAssociator);

    AccessRightValidator accessRightValidator = new AccessRightValidatorFactory().create(
        new AccessRightFilterParkingZone(),
        new AccessRightFilterDate(),
        new AccessRightFilterTime()
    );
    ParkingUserFactory parkingUserFactory = new ParkingUserFactory(new ParkingUserIdFactory(new IncrementalIdGenerator()));
    PermitNumberFactory permitNumberFactory = new PermitNumberFactory(new IncrementalIdGenerator());
    PermitFactoryCar permitFactoryCar = new PermitFactoryCar(permitNumberFactory,
                                                             accessRightValidator,
                                                             new CarFactory());
    PermitFactoryBike permitFactoryBike = new PermitFactoryBike(permitNumberFactory);
    PermitFactoryProvider permitFactoryProvider = new PermitFactoryProvider(permitFactoryCar, permitFactoryBike);
    AccessRightFactory accessRightFactory = new AccessRightFactory(new AccessPeriodFactory(new AccessPeriodFactoryHourly(),
                                                                                           new AccessPeriodFactorySingleDay(),
                                                                                           new AccessPeriodFactoryDayPerWeek(
                                                                                               calendar),
                                                                                           new AccessPeriodFactorySession(
                                                                                               calendar),
                                                                                           new AccessPeriodFactoryMonth()));
    InfractionFactory infractionFactory = new InfractionFactory(new InfractionIdFactory(new IncrementalIdGenerator()));
    InfractionCreator infractionCreator = new InfractionCreator(new InfractionTypeFactory(),
                                                                infractionFactory,
                                                                infractionAmountRepository);

    ParkingUserAssembler parkingUserAssembler = new ParkingUserAssembler(new PermitsAssembler(new AccessRightsAssembler(),
                                                                                              new CarAssembler()));
    ParkingUserUseCase parkingUserUseCase = new ParkingUserUseCase(parkingUserRepository,
                                                                   parkingUserFactory,
                                                                   parkingUserAssembler);
    ParkingPermitUseCase parkingPermitUseCase = new ParkingPermitUseCase(parkingUserRepository,
                                                                         permitFactoryProvider,
                                                                         new DeliveryInfosAssembler(),
                                                                         new PermitCreationInfosAssembler(
                                                                             new CarCreationInfosAssembler()),
                                                                         invoiceCreator,
                                                                         permitAssociator);
    ParkingAccessRightUseCase parkingAccessRightUseCase = new ParkingAccessRightUseCase(parkingUserRepository,
                                                                                        accessRightFactory,
                                                                                        new AccessPeriodCreationInfosAssembler(),
                                                                                        invoiceCreator,
                                                                                        accessRightAssociator);
    ParkingAccessUseCase parkingAccessUseCase = new ParkingAccessUseCase(parkingUserRepository, infractionCreator);

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
                                          invoiceUriBuilder);
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(parkingResource);
    // TODO add exception mapper
  }
}
