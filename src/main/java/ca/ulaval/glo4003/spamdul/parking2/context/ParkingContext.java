package ca.ulaval.glo4003.spamdul.parking2.context;

import ca.ulaval.glo4003.spamdul.invoice.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.ParkingResource;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessPeriodCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.CarDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitDeliveryAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactory;
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
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumberFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserIdFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.InfractionAmountRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence.ParkingUserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessRightsAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.CarAssembler;
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
                        InvoiceAssembler invoiceAssembler,
                        InvoiceDtoAssembler invoiceDtoAssembler) {
    Calendar calendar = new HardCodedCalendar();
    ParkingUserRepository parkingUserRepository = new ParkingUserRepositoryInMemory();
    InfractionAmountRepository infractionAmountRepository = new InfractionAmountRepositoryInMemory();
    // TODO populate infractionAmountRepository

    AccessRightValidator accessRightValidator = new AccessRightValidatorFactory().create(
        new AccessRightFilterParkingZone(),
        new AccessRightFilterDate(),
        new AccessRightFilterTime()
    );
    ParkingUserFactory parkingUserFactory = new ParkingUserFactory(new ParkingUserIdFactory(new IncrementalIdGenerator()));
    PermitFactory permitFactory = new PermitFactory(new PermitNumberFactory(new IncrementalIdGenerator()),
                                                    accessRightValidator,
                                                    new CarFactory());
    AccessRightFactory accessRightFactory = new AccessRightFactory(new AccessPeriodFactory(calendar));
    InfractionFactory infractionFactory = new InfractionFactory(new InfractionIdFactory(new IncrementalIdGenerator()));
    InfractionCreator infractionCreator = new InfractionCreator(new InfractionTypeFactory(),
                                                                infractionFactory,
                                                                infractionAmountRepository);

    ParkingAccessUseCase parkingAccessUseCase = new ParkingAccessUseCase(parkingUserRepository, infractionCreator);
    ParkingUseCase parkingUseCase = new ParkingUseCase(parkingUserRepository,
                                                       parkingUserFactory,
                                                       permitFactory,
                                                       accessRightFactory,
                                                       new DeliveryInfosAssembler(),
                                                       new PermitCreationInfosAssembler(),
                                                       new AccessPeriodCreationInfosAssembler(),
                                                       new ParkingUserAssembler(new PermitsAssembler(new AccessRightsAssembler(),
                                                                                                     new CarAssembler())),
                                                       invoiceAssembler,
                                                       invoiceCreator);

    UserCreationAssembler userCreationAssembler = new UserCreationAssembler();
    PermitCreationAssembler permitCreationAssembler = new PermitCreationAssembler(new PermitDeliveryAssembler());
    AccessRightCreationAssembler accessRightCreationAssembler = new AccessRightCreationAssembler(new AccessPeriodCreationAssembler());
    ParkingAccessAssembler parkingAccessAssembler = new ParkingAccessAssembler();
    parkingResource = new ParkingResource(userCreationAssembler,
                                          permitCreationAssembler,
                                          accessRightCreationAssembler,
                                          parkingAccessAssembler,
                                          parkingUseCase,
                                          parkingAccessUseCase,
                                          new ParkingUserDtoAssembler(new PermitDtoAssembler(new CarDtoAssembler(),
                                                                                             new AccessRightDtoAssembler())),
                                          invoiceDtoAssembler);
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(parkingResource);
    // TODO add exception mapper
  }
}
