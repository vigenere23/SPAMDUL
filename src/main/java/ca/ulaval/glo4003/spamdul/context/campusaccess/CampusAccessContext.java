package ca.ulaval.glo4003.spamdul.context.campusaccess;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.CampusAccessFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class CampusAccessContext implements ResourceContext {

  private final CampusAccessResource campusAccessResource;
  private final CampusAccessService campusAccessService;

  public CampusAccessContext(ParkingAccessLogger parkingAccessLogger,
                             CampusAccessTransactionService campusAccessTransactionService) {
    UserFactory userFactory = new UserFactory();
    UserService userService = new UserService(userFactory);

    CarFactory carFactory = new CarFactory();
    CarService carService = new CarService(carFactory);

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
    CsvReader csvReader = new CsvReader();
    CampusAccessFeeRepository campusAccessFeeRepository = new CampusAccessFeeCsvRepository(csvReader,
                                                                                           "src/main/resources/frais-acces.csv");
    campusAccessService = new CampusAccessService(userService,
                                                  carService,
                                                  campusAccessFactory,
                                                  campusAccessRepository,
                                                  calendar,
                                                  campusAccessFeeRepository,
                                                  campusAccessTransactionService);
    campusAccessService.register(parkingAccessLogger);
    campusAccessResource = new CampusAccessResourceImpl(campusAccessAssembler,
                                                        campusAccessService);
  }

  public CampusAccessService getCampusAccessService() {
    return campusAccessService;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(CampusAccessResource.class, campusAccessResource);
    resources.add(new AccessingCampusExceptionAssembler());
    resources.add(new CarExceptionAssembler());
  }
}
