package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.FundRaisingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCodeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeIdFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising.InMemoryInitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public abstract class FundraisingContext implements ResourceContext {

  private final FundraisingResource fundraisingResource;
  private final InitiativeCreator initiativeCreator;
  private final InitiativeRepository initiativeRepository;

  public FundraisingContext(InitiativeTransactionService initiativeTransactionService,
                            AuthenticationRepository authenticationRepository,
                            AccessTokenCookieAssembler cookieAssembler) {
    InitiativeIdFactory initiativeIdFactory = new InitiativeIdFactory(new IncrementalIdGenerator());
    InitiativeCodeFactory initiativeCodeFactory = new InitiativeCodeFactory(new IncrementalIdGenerator());
    InitiativeFactory initiativeFactory = new InitiativeFactory(initiativeIdFactory, initiativeCodeFactory);
    initiativeRepository = new InMemoryInitiativeRepository();

    InitiativeAssembler initiativeAssembler = new InitiativeAssembler();
    AccessLevelValidator accessLevelValidator = new FundRaisingAccessValidator(authenticationRepository);
    initiativeCreator = new InitiativeCreator(initiativeTransactionService, initiativeFactory);
    InitiativeService initiativeService = new InitiativeService(initiativeRepository,
                                                                initiativeCreator,
                                                                accessLevelValidator);

    fundraisingResource = new FundraisingResource(initiativeAssembler, initiativeService, cookieAssembler);

    InitiativePopulator populator = new InitiativePopulator(initiativeRepository, initiativeFactory);

    this.populateData(populator);
  }

  public InitiativeRepository getInitiativeRepository() {
    return initiativeRepository;
  }

  public InitiativeCreator getInitiativeCreator() {
    return initiativeCreator;
  }

  protected abstract void populateData(Populator populator);

  @Override public void registerResources(InstanceMap resources) {
    resources.add(fundraisingResource);
    resources.add(new InitiativeExceptionMapper());
  }
}
