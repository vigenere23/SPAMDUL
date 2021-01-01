package ca.ulaval.glo4003.spamdul.parking.context.campusaccess;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessCodeFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.campusaccess.CampusAccessFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.CampusAccessDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.CampusAccessUseCase;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogger;

public class CampusAccessContext implements ResourceContext {

  private final CampusAccessResource campusAccessResource;

  public CampusAccessContext(UserRepository userRepository, ParkingAccessLogger parkingAccessLogger,
                             CampusAccessTransactionService campusAccessTransactionService) {

    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();
    CampusAccessAssembler campusAccessAssembler = new CampusAccessAssembler(timePeriodAssembler);
    CampusAccessDtoAssembler campusAccessDtoAssembler = new CampusAccessDtoAssembler();

    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    CampusAccessCodeFactory campusAccessCodeFactory = new CampusAccessCodeFactory(new IncrementalIdGenerator());
    CampusAccessFactory campusAccessFactory = new CampusAccessFactory(campusAccessCodeFactory, timePeriodFactory);
    CsvReader csvReader = new CsvReader();
    CampusAccessFeeRepository campusAccessFeeRepository = new CampusAccessFeeCsvRepository(csvReader,
                                                                                           "src/main/resources/frais-acces.csv");
    CampusAccessUseCase campusAccessUseCase = new CampusAccessUseCase(campusAccessFactory,
                                                                      userRepository,
                                                                      calendar,
                                                                      campusAccessFeeRepository,
                                                                      campusAccessTransactionService,
                                                                      campusAccessDtoAssembler);
    campusAccessUseCase.register(parkingAccessLogger);
    campusAccessResource = new CampusAccessResource(campusAccessAssembler,
                                                    campusAccessUseCase);
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(campusAccessResource);
  }
}
