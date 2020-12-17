package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.InfractionsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionIdFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingDayOfWeekValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingPassExistsValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingPassValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingTimePeriodBoundaryValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.EmptyCarParkingPassCodeValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.infractions.InfractionsInfosJsonRepository;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.InfractionDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.InfractionUseCase;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;

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

    InfractionUseCase infractionUseCase = new InfractionUseCase(infractionInfoRepository,
                                                                userRepository,
                                                                infractionFactory,
                                                                firstValidationNode,
                                                                accessLevelValidator,
                                                                infractionTransactionService, infractionDtoAssembler);

    infractionResource = new InfractionResource(infractionAssembler, infractionUseCase, cookieAssembler);

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
