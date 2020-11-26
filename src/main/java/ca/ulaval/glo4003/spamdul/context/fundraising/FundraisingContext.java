package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.FundRaisingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativeBankAccount;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising.InMemoryInitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResourceImp;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;

public class FundraisingContext {

  private final InitiativePopulator initiativePopulator;
  private final FundraisingResource fundraisingResource;
  private final InitiativeCreator initiativeCreator;
  private final InitiativeRepository initiativeRepository;

  public FundraisingContext(InitiativeBankAccount initiativeBankAccount,
                            AuthenticationRepository authenticationRepository,
                            AccessTokenCookieAssembler cookieAssembler,
                            boolean populateData) {
    initiativeRepository = new InMemoryInitiativeRepository();

    InitiativeFactory initiativeFactory = new InitiativeFactory();

    InitiativeAssembler initiativeAssembler = new InitiativeAssembler();
    AccessLevelValidator accessLevelValidator = new FundRaisingAccessValidator(authenticationRepository);
    initiativeCreator = new InitiativeCreator(initiativeBankAccount, initiativeFactory);
    InitiativeService initiativeService = new InitiativeService(initiativeRepository,
                                                                initiativeCreator,
                                                                accessLevelValidator);

    fundraisingResource = new FundraisingResourceImp(initiativeAssembler, initiativeService, cookieAssembler);
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

  public InitiativeCreator getInitiativeCreator() {
    return initiativeCreator;
  }
}
