package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.InfractionsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionIdFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingDayOfWeekValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingPassExistsValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingPassValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingTimePeriodBoundaryValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.EmptyCarParkingPassCodeValidator;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsInfosJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionDtoAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;

public class InfractionsContext implements ResourceContext {

  private final InfractionResource infractionResource;

  public InfractionsContext(AuthenticationRepository authenticationRepository,
                            UserRepository userRepository,
                            AccessTokenCookieAssembler cookieAssembler,
                            InfractionTransactionService infractionTransactionService) {
    InfractionAssembler infractionAssembler = new InfractionAssembler();
    InfractionInfoRepository infractionInfoRepository = new InfractionsInfosJsonRepository(
        "src/main/resources/infraction.json",
        new JsonReader());
    CarParkingPassValidator firstValidationNode = initializeValidationChainAndReturnFirstNode(userRepository);

    InfractionIdFactory infractionIdFactory = new InfractionIdFactory(new IncrementalIdGenerator());
    InfractionFactory infractionFactory = new InfractionFactory(infractionIdFactory);

    AccessLevelValidator accessLevelValidator = new InfractionsAccessLevelValidator(authenticationRepository);

    InfractionDtoAssembler infractionDtoAssembler = new InfractionDtoAssembler();

    InfractionService infractionService = new InfractionService(infractionInfoRepository,
                                                                userRepository,
                                                                infractionFactory,
                                                                firstValidationNode,
                                                                accessLevelValidator,
                                                                infractionTransactionService, infractionDtoAssembler);

    infractionResource = new InfractionResource(infractionAssembler, infractionService, cookieAssembler);

  }

  private CarParkingPassValidator initializeValidationChainAndReturnFirstNode(UserRepository userRepository) {
    Calendar calendar = new HardCodedCalendar();
    UserFinderService userFinderService = new UserFinderService(userRepository);

    EmptyCarParkingPassCodeValidator emptyPassCodeValidator = new EmptyCarParkingPassCodeValidator();
    CarParkingPassExistsValidator passExistsValidator = new CarParkingPassExistsValidator(userFinderService);
    CarParkingZoneValidator parkingZoneValidator = new CarParkingZoneValidator(userFinderService);
    CarParkingTimePeriodBoundaryValidator timePeriodBoundaryValidator = new CarParkingTimePeriodBoundaryValidator(
        calendar,
        userFinderService);
    CarParkingDayOfWeekValidator dayOfWeekValidator = new CarParkingDayOfWeekValidator(calendar, userFinderService);

    emptyPassCodeValidator.setNextValidator(passExistsValidator);
    passExistsValidator.setNextValidator(parkingZoneValidator);
    parkingZoneValidator.setNextValidator(timePeriodBoundaryValidator);
    timePeriodBoundaryValidator.setNextValidator(dayOfWeekValidator);

    return emptyPassCodeValidator;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(infractionResource);
  }
}
