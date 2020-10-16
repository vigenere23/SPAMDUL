package ca.ulaval.glo4003.spamdul.context.sale;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.sale.PassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.car.InMemoryCarRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.pass.InMemoryPassRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.SaleResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.SaleResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;

public class SaleContext {

  private final SaleResource saleResource;
  private final PassRepository passRepository;

  public SaleContext() {
    passRepository = new InMemoryPassRepository();
    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    PassFactory passFactory = new PassFactory(timePeriodFactory);

    UserRepository userRepository = new UserRepositoryInMemory();
    UserFactory userFactory = new UserFactory();
    UserService userService = new UserService(userFactory, userRepository);

    CarRepository carRepository = new InMemoryCarRepository();
    CarFactory carFactory = new CarFactory();
    CarService carService = new CarService(carFactory, carRepository);

    CampusAccessRepository campusAccessRepository = new InMemoryCampusAccessRepository();
    CampusAccessFactory campusAccessFactory = new CampusAccessFactory(timePeriodFactory);
    CampusAccessService campusAccessService = new CampusAccessService(userService,
                                                                      carService,
                                                                      campusAccessFactory,
                                                                      campusAccessRepository,
                                                                      passRepository,
                                                                      calendar);

    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory();
    PassDeliveryOptionsFactory passDeliveryOptionsFactory = new PassDeliveryOptionsFactory();
    PassSender passSender = new PassSender(passDeliveryOptionsFactory, deliveryStrategyFactory);
    PassService passService = new PassService(passRepository, passFactory, campusAccessService, passSender);
    EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();
    PostalAddressAssembler postalAddressAssembler = new PostalAddressAssembler();
    DeliveryAssembler deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();
    PassSaleAssembler passSaleAssembler = new PassSaleAssembler(deliveryAssembler, timePeriodAssembler);
    saleResource = new SaleResourceImpl(passService, passSaleAssembler);
  }

  public SaleResource getSaleResource() {
    return saleResource;
  }

  public PassRepository getPassRepository() {
    return passRepository;
  }
}
