package ca.ulaval.glo4003.spamdul.context.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.car.InMemoryCarRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;

public class CampusAccessContext {

  private final CampusAccessResource campusAccessResource;

  public CampusAccessContext(PassRepository passRepository, ParkingAccessLogger parkingAccessLogger) {
    UserRepository userRepository = new UserRepositoryInMemory();
    UserFactory userFactory = new UserFactory();
    UserService userService = new UserService(userFactory, userRepository);

    CarRepository carRepository = new InMemoryCarRepository();
    CarFactory carFactory = new CarFactory();
    CarService carService = new CarService(carFactory, carRepository);

    UserAssembler userAssembler = new UserAssembler();
    CarAssembler carAssembler = new CarAssembler();
    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();
    CampusAccessAssembler campusAccessAssembler = new CampusAccessAssembler(userAssembler,
                                                                            carAssembler,
                                                                            timePeriodAssembler);

    CampusAccessRepository campusAccessRepository = new InMemoryCampusAccessRepository();
    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    CampusAccessFactory campusAccessFactory = new CampusAccessFactory(timePeriodFactory);
    CampusAccessService campusAccessService = new CampusAccessService(userService,
                                                                      carService,
                                                                      campusAccessFactory,
                                                                      campusAccessRepository,
                                                                      passRepository,
                                                                      calendar);
    campusAccessService.register(parkingAccessLogger);
    campusAccessResource = new CampusAccessResourceImpl(campusAccessAssembler,
                                                        campusAccessService);
  }

  public CampusAccessResource getCampusAccessResource() {
    return campusAccessResource;
  }
}
