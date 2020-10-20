package ca.ulaval.glo4003.spamdul.context.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionsJsonRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;

public class InfractionsContext {

  private final InfractionResource infractionResource;

  public InfractionsContext(PassRepository passRepository) {
    InfractionAssembler infractionAssembler = new InfractionAssembler();
    InfractionRepository infractionRepository = new InfractionsJsonRepository("src/main/resources/infraction.json",
                                                                              new JsonReader());
    PassValidator passValidator = new PassValidator();

    InfractionService infractionService = new InfractionService(infractionRepository, passRepository, passValidator);

    infractionResource = new InfractionResourceImpl(infractionAssembler, infractionService);

  }

  public InfractionResource getInfractionResource() {
    return infractionResource;
  }
}
