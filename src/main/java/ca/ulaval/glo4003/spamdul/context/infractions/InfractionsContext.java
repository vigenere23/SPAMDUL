package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.InfractionsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionIdFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.DayOfWeekValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.EmptyPassCodeValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.ParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.TimePeriodBoundaryValidator;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsInfosJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionExceptionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

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
    PassValidator firstValidationNode = initializeValidationChainAndReturnFirstNode(userRepository);

    InfractionIdFactory infractionIdFactory = new InfractionIdFactory(new IncrementalIdGenerator());
    InfractionFactory infractionFactory = new InfractionFactory(infractionIdFactory);

    AccessLevelValidator accessLevelValidator = new InfractionsAccessLevelValidator(authenticationRepository);

    InfractionService infractionService = new InfractionService(infractionInfoRepository,
                                                                userRepository,
                                                                infractionFactory,
                                                                firstValidationNode,
                                                                accessLevelValidator,
                                                                infractionTransactionService);

    infractionResource = new InfractionResourceImpl(infractionAssembler, infractionService, cookieAssembler);

  }

  private PassValidator initializeValidationChainAndReturnFirstNode(UserRepository userRepository) {
    Calendar calendar = new HardCodedCalendar();
    PassValidator.setPassRepository(userRepository);

    EmptyPassCodeValidator emptyPassCodeValidator = new EmptyPassCodeValidator();
    ParkingZoneValidator parkingZoneValidator = new ParkingZoneValidator();
    TimePeriodBoundaryValidator timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(calendar);
    DayOfWeekValidator dayOfWeekValidator = new DayOfWeekValidator(calendar);

    emptyPassCodeValidator.setNextValidator(parkingZoneValidator);
    parkingZoneValidator.setNextValidator(timePeriodBoundaryValidator);
    timePeriodBoundaryValidator.setNextValidator(dayOfWeekValidator);

    return emptyPassCodeValidator;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(infractionResource);
    resources.add(new InfractionExceptionAssembler());
  }
}
