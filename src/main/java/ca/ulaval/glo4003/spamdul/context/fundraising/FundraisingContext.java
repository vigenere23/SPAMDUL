package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.FundRaisingAccessValidator;
import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.FundraisingResource;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCodeFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeIdFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.initiatives.InMemoryInitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.InitiativeDtoAssembler;
import ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.InitiativeUseCase;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

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
    InitiativeDtoAssembler initiativeDtoAssembler = new InitiativeDtoAssembler();
    AccessLevelValidator accessLevelValidator = new FundRaisingAccessValidator(authenticationRepository);
    initiativeCreator = new InitiativeCreator(initiativeTransactionService, initiativeFactory);
    InitiativeUseCase initiativeUseCase = new InitiativeUseCase(initiativeRepository,
                                                                initiativeCreator,
                                                                accessLevelValidator,
                                                                initiativeDtoAssembler);

    fundraisingResource = new FundraisingResource(initiativeAssembler, initiativeUseCase, cookieAssembler);

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
  }
}
