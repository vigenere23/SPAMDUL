package ca.ulaval.glo4003.spamdul.context.campusaccess;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCodeFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parking.campusaccess.CampusAccessFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.CampusAccessExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessService;

public class CampusAccessContext implements ResourceContext {

  private final CampusAccessResource campusAccessResource;

  public CampusAccessContext(UserRepository userRepository, ParkingAccessLogger parkingAccessLogger,
                             CampusAccessTransactionService campusAccessTransactionService) {

    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();
    CampusAccessAssembler campusAccessAssembler = new CampusAccessAssembler(timePeriodAssembler);

    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    CampusAccessCodeFactory campusAccessCodeFactory = new CampusAccessCodeFactory(new IncrementalIdGenerator());
    CampusAccessFactory campusAccessFactory = new CampusAccessFactory(campusAccessCodeFactory, timePeriodFactory);
    CsvReader csvReader = new CsvReader();
    CampusAccessFeeRepository campusAccessFeeRepository = new CampusAccessFeeCsvRepository(csvReader,
                                                                                           "src/main/resources/frais-acces.csv");
    CampusAccessService campusAccessService = new CampusAccessService(campusAccessFactory,
                                                                      userRepository,
                                                                      calendar,
                                                                      campusAccessFeeRepository,
                                                                      campusAccessTransactionService);
    campusAccessService.register(parkingAccessLogger);
    campusAccessResource = new CampusAccessResource(campusAccessAssembler,
                                                    campusAccessService);
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(campusAccessResource);
    resources.add(new CampusAccessExceptionAssembler());
    resources.add(new AccessingCampusExceptionAssembler());
    resources.add(new CarExceptionAssembler());
  }
}
