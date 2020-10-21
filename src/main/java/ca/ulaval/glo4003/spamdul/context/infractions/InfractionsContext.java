package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.ParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.TimePeriodValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.ValidationChain;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InMemoryInfractionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.transactions.InMemoryTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class InfractionsContext {

  private final InfractionResource infractionResource;

  public InfractionsContext(PassRepository passRepository) {
    InfractionAssembler infractionAssembler = new InfractionAssembler();
    InfractionInfoRepository infractionInfoRepository = new InfractionsJsonRepository(
        "src/main/resources/infraction.json",
        new JsonReader());
    InfractionRepository infractionRepository = new InMemoryInfractionRepository();
    ValidationChain validationChain = InitilalizeValidationChain();
    TransactionRepository transactionRepository = new InMemoryTransactionRepository();
    TransactionFactory transactionFactory = new TransactionFactory();
    TransactionService transactionService = new TransactionService(transactionRepository, transactionFactory);

    InfractionService infractionService = new InfractionService(infractionInfoRepository,
                                                                infractionRepository,
                                                                passRepository,
                                                                validationChain,
                                                                transactionService);

    infractionResource = new InfractionResourceImpl(infractionAssembler, infractionService);

  }

  public InfractionResource getInfractionResource() {
    return infractionResource;
  }

  private ValidationChain InitilalizeValidationChain() {
    ParkingZoneValidator parkingZoneValidator = new ParkingZoneValidator();
    TimePeriodValidator timePeriodValidator = new TimePeriodValidator();

    parkingZoneValidator.setNextValidator(timePeriodValidator);

    ValidationChain validationChain = new ValidationChain(parkingZoneValidator);

    return validationChain;
  }
}
