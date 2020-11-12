package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising.InitiativeRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResourceImp;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;

public class FundraisingContext {

  private final InitiativePopulator initiativePopulator;
  private final FundraisingResource fundraisingResource;
  private final InitiativeFactory initiativeFactory;
  private final InitiativeRepository initiativeRepository;

  public FundraisingContext(BankRepository bankRepository,
                            boolean populateData) {
    initiativeRepository = new InitiativeRepositoryInMemory();

    initiativeFactory = new InitiativeFactory();
    TransactionFactory transactionFactory = new TransactionFactory();

    InitiativeAssembler initiativeAssembler = new InitiativeAssembler();
    InitiativeService initiativeService = new InitiativeService(initiativeRepository,
                                                                initiativeFactory,
                                                                bankRepository,
                                                                transactionFactory);

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

  public InitiativeRepository getInitiativeRepository() {
    return initiativeRepository;
  }

  public InitiativeFactory getInitiativeFactory() {
    return initiativeFactory;
  }
}
