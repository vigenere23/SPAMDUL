package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising.InitiativeRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResourceImp;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;

public class FundraisingContext {

  private final InitiativePopulator initiativePopulator;
  private final FundraisingResource fundraisingResource;

  public FundraisingContext(boolean populateData) {
    InitiativeRepository initiativeRepository = new InitiativeRepositoryInMemory();
    InitiativeFactory initiativeFactory = new InitiativeFactory();
    InitiativeAssembler initiativeAssembler = new InitiativeAssembler(initiativeFactory);
    InitiativeService initiativeService = new InitiativeService(initiativeRepository);

    fundraisingResource = new FundraisingResourceImp(initiativeAssembler, initiativeService);
    initiativePopulator = new InitiativePopulator(initiativeRepository, initiativeFactory);

    if (populateData) {
      this.populateData();
    }
  }

  private void populateData() {
    final int NUMBER_OF_RECORDS = 30;
    initiativePopulator.populate(NUMBER_OF_RECORDS);
  }

  public FundraisingResource getFundraisingResource() {
    return fundraisingResource;
  }
}
