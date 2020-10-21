package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.*;
import ca.ulaval.glo4003.spamdul.entity.infractions.ValidationChain;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InMemoryInfractionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsInfosJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class InfractionsContext {

  private final InfractionResource infractionResource;

  public InfractionsContext(PassRepository passRepository, TransactionRepository transactionRepository) {
    InfractionAssembler infractionAssembler = new InfractionAssembler();
    InfractionInfoRepository infractionInfoRepository = new InfractionsInfosJsonRepository(
        "src/main/resources/infraction.json",
        new JsonReader());
    InfractionRepository infractionRepository = new InMemoryInfractionRepository();
    ValidationChain validationChain = initializeValidationChain(passRepository);
    TransactionFactory transactionFactory = new TransactionFactory();
    TransactionService transactionService = new TransactionService(transactionRepository, transactionFactory);
    InfractionFactory infractionFactory = new InfractionFactory();

    InfractionService infractionService = new InfractionService(infractionInfoRepository,
                                                                infractionRepository,
                                                                validationChain,
                                                                transactionService,
                                                                infractionFactory);

    infractionResource = new InfractionResourceImpl(infractionAssembler, infractionService);

  }

  public InfractionResource getInfractionResource() {
    return infractionResource;
  }

  private ValidationChain initializeValidationChain(PassRepository passRepository) {
    Calendar calendar = new HardCodedCalendar();

    ParkingZoneValidator parkingZoneValidator = new ParkingZoneValidator(passRepository);
    EmptyPassCodeValidator emptyPassCodeValidator = new EmptyPassCodeValidator(passRepository);
    InvalidPassCodeFormatValidator invalidPassCodeFormatValidator = new InvalidPassCodeFormatValidator(passRepository
    );
    PassExistsValidator passExistsValidator = new PassExistsValidator(passRepository);
    TimePeriodBoundaryValidator timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(passRepository, calendar
    );
    DayOfWeekValidator dayOfWeekValidator = new DayOfWeekValidator(passRepository, calendar);


    parkingZoneValidator.setNextValidator(emptyPassCodeValidator);
    emptyPassCodeValidator.setNextValidator(invalidPassCodeFormatValidator);
    invalidPassCodeFormatValidator.setNextValidator(passExistsValidator);
    passExistsValidator.setNextValidator(timePeriodBoundaryValidator);
    timePeriodBoundaryValidator.setNextValidator(dayOfWeekValidator);

    return new ValidationChain(parkingZoneValidator);
  }
}
