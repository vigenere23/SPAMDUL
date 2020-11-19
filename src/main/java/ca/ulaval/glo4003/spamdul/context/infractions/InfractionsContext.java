package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.InfractionsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.DayOfWeekValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.EmptyPassCodeValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.ParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassCodeFormatValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassExistsValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.TimePeriodBoundaryValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.InMemoryAuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InMemoryInfractionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsInfosJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;

public class InfractionsContext {

  private final InfractionResource infractionResource;

  public InfractionsContext(PassRepository passRepository,
                            BankRepository bankRepository,
                            AuthenticationRepository authenticationRepository,
                            AccessTokenCookieAssembler cookieAssembler) {
    InfractionAssembler infractionAssembler = new InfractionAssembler();
    InfractionInfoRepository infractionInfoRepository = new InfractionsInfosJsonRepository(
        "src/main/resources/infraction.json",
        new JsonReader());
    InfractionRepository infractionRepository = new InMemoryInfractionRepository();
    PassValidator firstValidationNode = initializeValidationChainAndReturnFirstNode(passRepository);

    TransactionFactory transactionFactory = new TransactionFactory();
    InfractionFactory infractionFactory = new InfractionFactory();

    AccessLevelValidator accessLevelValidator = new InfractionsAccessLevelValidator(authenticationRepository);

    InfractionService infractionService = new InfractionService(infractionInfoRepository,
                                                                infractionRepository,
                                                                infractionFactory,
                                                                firstValidationNode,
                                                                transactionFactory,
                                                                bankRepository,
                                                                accessLevelValidator);

    infractionResource = new InfractionResourceImpl(infractionAssembler, infractionService, cookieAssembler);

  }

  public InfractionResource getInfractionResource() {
    return infractionResource;
  }

  private PassValidator initializeValidationChainAndReturnFirstNode(PassRepository passRepository) {
    Calendar calendar = new HardCodedCalendar();
    PassValidator.setPassRepository(passRepository);

    EmptyPassCodeValidator emptyPassCodeValidator = new EmptyPassCodeValidator();
    PassCodeFormatValidator passCodeFormatValidator = new PassCodeFormatValidator();
    PassExistsValidator passExistsValidator = new PassExistsValidator();
    ParkingZoneValidator parkingZoneValidator = new ParkingZoneValidator();
    TimePeriodBoundaryValidator timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(calendar);
    DayOfWeekValidator dayOfWeekValidator = new DayOfWeekValidator(calendar);

    emptyPassCodeValidator.setNextValidator(passCodeFormatValidator);
    passCodeFormatValidator.setNextValidator(passExistsValidator);
    passExistsValidator.setNextValidator(parkingZoneValidator);
    parkingZoneValidator.setNextValidator(timePeriodBoundaryValidator);
    timePeriodBoundaryValidator.setNextValidator(dayOfWeekValidator);

    return emptyPassCodeValidator;
  }
}
